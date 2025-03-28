package pack.mangaverse.data

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pack.mangaverse.data.models.DateDebutM
import pack.mangaverse.data.models.MediaDataM
import pack.mangaverse.data.models.PersonnageDetailsM
import pack.mangaverse.data.models.PersonnageM

class MangaVerseViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _mediaList = MutableLiveData<List<MediaDataM>>()
    val mediaList: LiveData<List<MediaDataM>> = _mediaList

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchAllMedia()
    }

    private fun fetchAllMedia() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("contenu")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot != null && !snapshot.isEmpty) {
                            val mediaItems = snapshot.documents.mapNotNull { document ->
                                // Récupération des valeurs manuellement
                                val mediaId = document.getString("id") ?: ""
                                val title = document.getString("titre")
                                val format = document.getString("format")
                                val type = document.getString("type")
                                val status = document.getString("status")
                                val description = document.getString("description")
                                val startDate = document.get("dateDebut") as? Map<String, Any>
                                val episodes = document.getLong("nbEpisodes")?.toInt() ?: 0
                                val source = document.getString("sources")
                                val coverImage = document.getString("coverPath")
                                val bannerImage = document.getString("bannerPath")
                                val genres = document.get("genres") as? List<String> ?: emptyList()

                                val characters = (document.get("personnages") as? List<Map<String, Any>>)?.map { character ->
                                    val role = character["role"] as? String
                                    val personnage = character["personnage"] as? Map<String, Any>
                                    val image = personnage?.get("image") as? String
                                    val name = personnage?.get("name") as? String
                                    val description = personnage?.get("description") as? String
                                    val id = personnage?.get("id") as? String
                                    PersonnageM(role, PersonnageDetailsM(image, name, description, id))
                                } ?: emptyList()

                                // Attribution manuelle des valeurs
                                MediaDataM(
                                    mediaId,
                                    title,
                                    format,
                                    type,
                                    status,
                                    description,
                                    startDate?.let { DateDebutM(it["year"] as? Int ?: 0, it["month"] as? Int ?: 0, it["day"] as? Int ?: 0) },
                                    episodes,
                                    source,
                                    coverImage,
                                    bannerImage,
                                    characters,
                                    genres
                                )
                            }

                            // Mise à jour de la liste des médias
                            _mediaList.postValue(mediaItems)
                            Log.d("MangaVerseViewModel", "Médias récupérés avec succès. Nombre de médias: ${mediaItems.size}")
                        } else {
                            Log.d("MangaVerseViewModel", "Aucun média trouvé dans Firestore")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("MangaVerseViewModel", "Erreur lors du chargement des médias: ${e.message}")
                    }
                    .addOnCompleteListener {
                        _isLoading.postValue(false)  // Mark loading as complete
                    }
            } catch (e: Exception) {
                Log.e("MangaVerseViewModel", "Erreur lors de la récupération des médias", e)
                _isLoading.postValue(false)
            }
        }
    }

    fun getMediaById(mediaId: String): MediaDataM? {
        return try {
            // Check if the list is not null or empty before trying to find the media
            val media = _mediaList.value?.firstOrNull { it.mediaId == mediaId }

            if (media == null) {
                Log.e("MangaVerseViewModel", "Aucun média trouvé avec l'ID: $mediaId")
            }

            media
        } catch (e: Exception) {
            Log.e("MangaVerseViewModel", "Erreur lors de la récupération du média avec l'ID: $mediaId", e)
            null
        }
    }


}


