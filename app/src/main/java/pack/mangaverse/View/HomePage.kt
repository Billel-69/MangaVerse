package pack.mangaverse.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.R
import pack.mangaverse.data.DataFactory
import pack.mangaverse.data.DataManager
import pack.mangaverse.data.models.Contenu
import pack.mangaverse.destinations.CatalogueScreenDestination
import pack.mangaverse.destinations.ChoiceScreenDestination
import pack.mangaverse.destinations.LoginScreenDestination

@Destination(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator,modifier: Modifier =Modifier) {
    val db = FirebaseFirestore.getInstance()
    val df = DataFactory(db)
    val dm = DataManager(df)

    dm.updateAllLists(
        onComplete = {
            println("All lists have been updated successfully!")
            println("Users: ${dm.getUtilisateurs()}")
        },
        onFailure = { exception ->
            println("Error occurred: ${exception.message}")
        }
    )

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.mangaverse),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Button(
                onClick = {navigator.navigate(CatalogueScreenDestination ())},
                modifier = Modifier.height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.catalogueicon),
                    contentDescription = "Catalogue",
                    modifier = Modifier.size(32.dp)
                )
            }

            Button(
                onClick = {  navigator.navigate(ChoiceScreenDestination()) },
                modifier = Modifier.height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profileicon),
                    contentDescription = "Se connecter",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CatalogueScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Catalogue", color = Color.White)
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profil", color = Color.White)
    }
}