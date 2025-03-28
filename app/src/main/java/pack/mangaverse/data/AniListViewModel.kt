import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import pack.mangaverse.data.models.AniListResponse
import pack.mangaverse.data.models.Contenu
import pack.mangaverse.data.models.DateDebut
import pack.mangaverse.data.models.Personnage
import pack.mangaverse.data.models.PersonnageRole

class AniListViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val client = OkHttpClient()

    // Fetch and store media data
    fun fetchAndStoreMedia(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var query = """
                    query (${'$'}id: Int) {
                        Media(id: ${'$'}id) {
                            id
                            title {
                                romaji
                            }
                            format
                            type
                            status
                            description
                            startDate {
                                year
                                month
                                day
                            }
                            episodes
                            source
                            coverImage {
                                extraLarge
                            }
                            bannerImage
                            characters {
                                edges {
                                    node {
                                        id
                                        name {
                                            full
                                        }
                                        image {
                                            large
                                        }
                                        description
                                    }
                                    role
                                }
                            }
                            genres
                        }
                    }
                """

                var json = Gson().toJson(mapOf("query" to query, "variables" to mapOf("id" to id)))
                var body = json.toRequestBody("application/json".toMediaTypeOrNull())

                val request = Request.Builder()
                    .url("https://graphql.anilist.co/")
                    .post(body)
                    .build()

                var response = client.newCall(request).execute()
                var responseBody = response.body?.string()

                var result = Gson().fromJson(responseBody, AniListResponse::class.java)
                var media = result.data?.Media

                if (media == null) {
                    Log.d("AniListImport", "No media found for anime, trying manga.")

                    query = """
                        query (${'$'}id: Int) {
                            Media(id: ${'$'}id, type: MANGA) {
                                id
                                title {
                                    romaji
                                }
                                format
                                type
                                status
                                description
                                startDate {
                                    year
                                    month
                                    day
                                }
                                episodes
                                source
                                coverImage {
                                    extraLarge
                                }
                                bannerImage
                                characters {
                                    edges {
                                        node {
                                            id
                                            name {
                                                full
                                            }
                                            image {
                                                large
                                            }
                                            description
                                        }
                                        role
                                    }
                                }
                                genres
                            }
                        }
                    """
                    json = Gson().toJson(mapOf("query" to query, "variables" to mapOf("id" to id)))
                    body = json.toRequestBody("application/json".toMediaTypeOrNull())

                    response = client.newCall(request).execute()
                    responseBody = response.body?.string()

                    result = Gson().fromJson(responseBody, AniListResponse::class.java)
                    media = result.data?.Media
                }

                if (media != null) {
                    val dateDebut = DateDebut(
                        year = media.startDate?.year,
                        month = media.startDate?.month,
                        day = media.startDate?.day
                    )

                    val personnages = media.characters?.edges?.map { edge ->
                        PersonnageRole(
                            personnage = Personnage(
                                id = edge.node.id,
                                name = edge.node.name.full,
                                image = edge.node.image?.large ?: "",
                                description = edge.node.description ?: ""
                            ),
                            role = edge.role ?: ""
                        )
                    } ?: emptyList()

                    val contenu = Contenu(
                        id = media.id.toString(),
                        titre = media.title?.romaji ?: "Titre inconnu",
                        format = media.format ?: "",
                        type = media.type ?: "",
                        status = media.status ?: "",
                        description = media.description ?: "",
                        dateDebut = dateDebut,
                        nbEpisodes = media.episodes ?: 0,
                        sources = media.source ?: "",
                        coverPath = media.coverImage?.extraLarge ?: "",
                        bannerPath = media.bannerImage ?: "",
                        personnages = personnages,
                        genres = media.genres ?: emptyList()
                    )

                    firestore.collection("contenu")
                        .document(contenu.id)
                        .set(contenu)
                        .addOnSuccessListener {
                            Log.d("AniListImport", "Media ajouté avec succès à Firestore!")
                        }
                        .addOnFailureListener { e ->
                            Log.e("AniListImport", "Erreur lors de l'ajout du média à Firestore: ${e.message}")
                        }
                } else {
                    Log.e("AniListImport", "Aucun média trouvé dans la réponse")
                }

            } catch (e: Exception) {
                Log.e("AniListImport", "Erreur lors de la récupération ou de l'envoi des données", e)
            }
        }
    }

    fun fetchAndStoreMultipleMedia(startId: Int, numberOfItems: Int) {
        for (i in 0 until numberOfItems) {
            val mediaId = startId + i
            fetchAndStoreMedia(mediaId)
        }
    }
}
