package pack.mangaverse.view

//import AniListViewModel
import AppHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.data.MangaVerseViewModel
import pack.mangaverse.destinations.CataloguePageDestination
import pack.mangaverse.destinations.DetailPageDestination
import pack.mangaverse.view.component.MangaCard
import pack.mangaverse.view.component.MangaSection

@Destination(start = true)
@Composable
fun HomePage(navigator: DestinationsNavigator) {
    val MVViewModel = MangaVerseViewModel()
    val firstTenItems = MVViewModel.mediaList.observeAsState(listOf()).value.take(10)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        AppHeader(navigator)

        Text(
            text = "Accueil",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "Bienvenue sur notre catalogue d'Animes et Mangas ! Explorez une large sélection de vos médias préférés. Découvrez les nouveautés, les classiques et bien plus encore !",
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            for (media in firstTenItems) {
                item {
                    MangaCard(
                        MediaData = media,
                        modifier = Modifier
                            .clickable {
                                navigator.navigate(DetailPageDestination(media= media))
                            }
                    )
                }
            }
        }

        Button(
            onClick = {
                navigator.navigate(CataloguePageDestination)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text(text = "Voir plus", color = Color.White)
        }
    }
}