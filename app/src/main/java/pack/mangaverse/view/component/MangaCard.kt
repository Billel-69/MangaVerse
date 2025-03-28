package pack.mangaverse.view.component

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import pack.mangaverse.R
import pack.mangaverse.data.models.MediaDataM
import pack.mangaverse.data.models.Utilisateur

@Composable
fun MangaCard(MediaData: MediaDataM, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    var isFavorited by remember { mutableStateOf(false) }

    val painter =
        rememberAsyncImagePainter(ImageRequest.Builder
            (context).data(data = MediaData.coverImage).apply(block = fun ImageRequest.Builder.() {
            placeholder(R.drawable.mangaverse)
            error(R.drawable.mangaverse)
            crossfade(500)
            listener(
                onError = { request, errorResult ->
                    Log.e("MangaCard", "Error loading image from URL: ${request.data}")
                    val throwableMessage = errorResult?.throwable?.message ?: "Unknown error"
                    Log.e("MangaCard", "Error message: $throwableMessage")
                    Log.e("MangaCard", "Error result: $errorResult")
                },
                onSuccess = { request, metadata ->
                    Log.d("MangaCard", "Image loaded successfully: ${request.data}")
                }
            )
        }).build()
        )

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            val userDocRef = firestore.collection("utilisateur").document(currentUser.uid)
            userDocRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val utilisateur = document.toObject(Utilisateur::class.java)
                    isFavorited = utilisateur?.favoris?.contains(MediaData.mediaId) == true
                }
            }
        }
    }

    Box(
        modifier = modifier
            .width(150.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.DarkGray)
    ) {
        Image(
            painter = painter,
            contentDescription = MediaData.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (currentUser != null) {
            IconButton(
                onClick = {
                    if (checkInternetConnection(context)) {
                        isFavorited = !isFavorited

                        if (isFavorited) {
                            addToFavorites(currentUser.uid, MediaData.mediaId)
                        } else {
                            removeFromFavorites(currentUser.uid, MediaData.mediaId)
                        }
                    } else {
                        Toast.makeText(context, "Pas de connexion Internet", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorited) "Favorited" else "Add to favorites",
                    tint = Color.Red
                )
            }
        }
    }
}

fun addToFavorites(userId: String, mediaId: String) {
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("utilisateur").document(userId)
        .update("favoris", FieldValue.arrayUnion(mediaId))
        .addOnSuccessListener {
            Log.d("MangaCard", "Successfully added to favorites")
        }
        .addOnFailureListener { e ->
            Log.e("MangaCard", "Error adding to favorites: ${e.message}")
        }
}

fun removeFromFavorites(userId: String, mediaId: String) {
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("utilisateur").document(userId)
        .update("favoris", FieldValue.arrayRemove(mediaId))
        .addOnSuccessListener {
            Log.d("MangaCard", "Successfully removed from favorites")
        }
        .addOnFailureListener { e ->
            Log.e("MangaCard", "Error removing from favorites: ${e.message}")
        }
}

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}
