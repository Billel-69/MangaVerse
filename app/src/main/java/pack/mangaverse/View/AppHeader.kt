import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.R
import pack.mangaverse.destinations.CatalogueScreenDestination
import pack.mangaverse.destinations.ChoiceScreenDestination

@Composable
fun AppHeader(navigator: DestinationsNavigator) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 🔁 LOGO CLIQUABLE
        Image(
            painter = painterResource(id = R.drawable.mangaverse),
            contentDescription = "Logo",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .clickable { navigator.navigate(pack.mangaverse.destinations.HomeScreenDestination()) } // navigation vers accueil
        )

        // 🔎 BARRE DE RECHERCHE
        TextField(
            value = "", // Tu peux utiliser un `remember { mutableStateOf("") }` pour rendre dynamique
            onValueChange = {},
            placeholder = { Text("Rechercher...", color = Color.Gray) },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .height(48.dp),

        )

        // ⚙️ Boutons
        Row {
            IconButton(onClick = { navigator.navigate(CatalogueScreenDestination()) }) {
                Icon(
                    painter = painterResource(id = R.drawable.catalogueicon),
                    contentDescription = "Catalogue",
                    tint = Color.White
                )
            }

            IconButton(onClick = { navigator.navigate(ChoiceScreenDestination()) }) {
                Icon(
                    painter = painterResource(id = R.drawable.profileicon),
                    contentDescription = "Se connecter",
                    tint = Color.White
                )
            }
        }
    }
}
