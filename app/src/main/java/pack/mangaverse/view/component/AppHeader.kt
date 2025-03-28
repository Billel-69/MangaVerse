import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.R
import pack.mangaverse.data.models.Utilisateur
import pack.mangaverse.destinations.CataloguePageDestination
import pack.mangaverse.destinations.LogPageDestination
import pack.mangaverse.destinations.ProfilPageDestination

@Composable
fun AppHeader(navigator: DestinationsNavigator) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current
    var userProfile by remember { mutableStateOf<Utilisateur?>(null) }
    var loadingProfile by remember { mutableStateOf(true) }

    LaunchedEffect(user) {
        if (user != null) {
            Log.d("AppHeader", "Utilisateur connecté: ${user.uid}")
            val db = FirebaseFirestore.getInstance()
            db.collection("utilisateur").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userProfile = document.toObject(Utilisateur::class.java)
                        Log.d("AppHeader", "Profil récupéré: $userProfile")
                    } else {
                        Log.e("AppHeader", "Aucun profil trouvé pour l'utilisateur")
                    }
                    loadingProfile = false
                }
                .addOnFailureListener {
                    Log.e("AppHeader", "Erreur de chargement du profil: ${it.message}")
                    Toast.makeText(context, "Erreur de chargement du profil", Toast.LENGTH_SHORT).show()
                    loadingProfile = false
                }
        } else {
            Log.d("AppHeader", "Aucun utilisateur connecté")
            loadingProfile = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.mangaverse),
            contentDescription = "Logo",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .clickable { navigator.navigate(pack.mangaverse.destinations.HomePageDestination()) }
        )

        Row {
            // Navigate to Catalogue Page
            IconButton(onClick = { navigator.navigate(CataloguePageDestination()) }) {
                Icon(
                    painter = painterResource(id = R.drawable.catalogueicon),
                    contentDescription = "Catalogue",
                    tint = Color.White
                )
            }

            IconButton(onClick = {
                if (user != null) {
                    navigator.navigate(ProfilPageDestination())
                } else {
                    Log.d("AppHeader", "Utilisateur non connecté")
                    navigator.navigate(LogPageDestination())
                }
            }) {
                if (userProfile?.pdp != null) {
                    // Display user's profile picture
                    AsyncImage(
                        model = userProfile?.pdp,
                        contentDescription = "Profil",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = "Profil",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

