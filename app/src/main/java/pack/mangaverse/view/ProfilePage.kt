package pack.mangaverse.view

import AppHeader
import android.util.Log
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.R
import pack.mangaverse.data.MangaVerseViewModel
import pack.mangaverse.data.models.MediaDataM
import pack.mangaverse.data.models.Utilisateur
import pack.mangaverse.destinations.LogPageDestination

@Destination
@Composable
fun ProfilPage(navigator: DestinationsNavigator) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    val MVViewModel = MangaVerseViewModel()

    var utilisateur by remember { mutableStateOf<Utilisateur?>(null) }
    var favorisMedia by remember { mutableStateOf<List<MediaDataM>>(emptyList()) }
    var filteredFavorisMedia by remember { mutableStateOf<List<MediaDataM>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    var newPseudo by remember { mutableStateOf("") }
    var isEditingPseudo by remember { mutableStateOf(false) }

    val uid = auth.currentUser?.uid

    LaunchedEffect(uid) {
        if (uid == null) {
            loading = false
            return@LaunchedEffect
        }

        firestore.collection("utilisateur").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    utilisateur = document.toObject(Utilisateur::class.java)
                    utilisateur?.favoris?.let { favoris ->
                        loadFavorites(favoris, viewModel = MVViewModel) { favorisList ->
                            favorisMedia = favorisList
                            filteredFavorisMedia = favorisList
                            loading = false
                        }
                    } ?: run {
                        loading = false
                    }
                } else {
                    loading = false
                }
            }
            .addOnFailureListener { e ->
                loading = false
            }
    }

    val favorisListForSearch = favorisMedia

    val filteredList = if (searchQuery.isEmpty()) {
        favorisListForSearch
    } else {
        favorisListForSearch.filter {
            it.title?.contains(searchQuery, ignoreCase = true) == true
        }
    }

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uid == null || utilisateur == null) {
            navigator.navigate(LogPageDestination()) {
                popUpTo("ProfilPage") { inclusive = true }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                AppHeader(navigator)

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState
                ) {
                    item {
                        val profilePicture = if (utilisateur!!.pdp.isNotEmpty()) {
                            utilisateur!!.pdp
                        } else {
                            "https://m.media-amazon.com/images/M/MV5BMTQ5Nzg2MTgwMl5BMl5BanBnXkFtZTcwNTA0NjcxMw@@._V1_FMjpg_UX1000_.jpg"
                        }

                        Image(
                            painter = rememberAsyncImagePainter(profilePicture),
                            contentDescription = "Photo de profil",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = utilisateur!!.pseudo,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { isEditingPseudo = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                        ) {
                            Text("Changer pseudo", color = Color.White)
                        }

                        if (isEditingPseudo) {
                            OutlinedTextField(
                                value = newPseudo,
                                onValueChange = { newPseudo = it },
                                label = { Text("Nouveau pseudo") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (newPseudo.isNotEmpty()) {
                                        firestore.collection("utilisateur").document(uid!!)
                                            .update("pseudo", newPseudo)
                                            .addOnSuccessListener {
                                                utilisateur?.pseudo = newPseudo
                                                isEditingPseudo = false
                                            }
                                            .addOnFailureListener {
                                            }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                            ) {
                                Text("Confirmer", color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    isEditingPseudo = false
                                    newPseudo = ""
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                            ) {
                                Text("Annuler", color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Text(
                            text = "Favoris",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Rechercher") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (filteredList.isEmpty()) {
                        item {
                            Text(text = "Aucun favori ajouté", color = Color.White)
                        }
                    } else {
                        items(filteredList) { media ->
                            FavoriteItem(
                                media,
                                onRemoveFavorite = { mediaId ->
                                    removeFromFavorites(mediaId, firestore, MVViewModel) { updatedFavorites ->
                                        favorisMedia = updatedFavorites
                                        filteredFavorisMedia = updatedFavorites
                                    }
                                }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                auth.signOut()
                                navigator.navigate(LogPageDestination()) {
                                    popUpTo("ProfilPage") { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Se déconnecter", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FavoriteItem(
    media: MediaDataM,
    onRemoveFavorite: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1F1F)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = media.coverImage,
                    placeholder = painterResource(id = R.drawable.mangaverse),
                    error = painterResource(id = R.drawable.catalogueicon)
                ),
                contentDescription = media.title,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = media.title ?: "No Title",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                media.genres.takeIf { it.isNotEmpty() }?.let {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        media.genres.forEach { genre ->
                            AssistChip(
                                onClick = {},
                                label = { Text(genre, style = TextStyle(fontSize = 8.sp)) },  // Even smaller text
                                shape = RoundedCornerShape(20.dp),
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFF6200EE),
                                    labelColor = Color.White
                                ),
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                IconButton(
                    onClick = { onRemoveFavorite(media.mediaId ?: "") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove from favorites",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

fun removeFromFavorites(mediaId: String, firestore: FirebaseFirestore, viewModel: MangaVerseViewModel, onResult: (List<MediaDataM>) -> Unit) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if (uid != null) {
        firestore.collection("utilisateur").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val utilisateur = document.toObject(Utilisateur::class.java)
                    val updatedFavoris = utilisateur?.favoris?.toMutableList()
                    updatedFavoris?.remove(mediaId)

                    firestore.collection("utilisateur").document(uid)
                        .update("favoris", updatedFavoris)
                        .addOnSuccessListener {
                            val updatedFavorisList = viewModel.mediaList.value?.filter { it.mediaId != mediaId } ?: emptyList()
                            onResult(updatedFavorisList)
                        }
                        .addOnFailureListener { e ->
                        }
                }
            }
    }
}

fun loadFavorites(
    ids: List<String>,
    viewModel: MangaVerseViewModel,
    onResult: (List<MediaDataM>) -> Unit
) {
    if (ids.isEmpty()) {
        Log.d("loadFavorites", "Aucun favoris trouvé")
        onResult(emptyList())
        return
    }

    Log.d("loadFavorites", "Chargement des favoris : $ids")
    val favorisList = mutableListOf<MediaDataM>()

    viewModel.mediaList.observeForever { mediaList ->
        if (mediaList != null) {
            var loadedCount = 0

            for (id in ids) {
                val media = viewModel.getMediaById(id)

                if (media != null) {
                    Log.d("loadFavorites", "Favori trouvé localement: $media")
                    favorisList.add(media)
                } else {
                    Log.d("loadFavorites", "Favori non trouvé localement pour l'ID: $id")
                }

                loadedCount++

                if (loadedCount == ids.size) {
                    Log.d("loadFavorites", "Favoris chargés : $favorisList")
                    onResult(favorisList)
                }
            }
        }
    }
}
