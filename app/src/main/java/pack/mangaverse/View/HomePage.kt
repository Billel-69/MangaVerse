package pack.mangaverse.View

import AppHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.data.DataFactory
import pack.mangaverse.data.DataManager
import pack.mangaverse.data.MangaRepository

@Destination(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val mangasPopulaires = MangaRepository.getMangasPopulaires()
    val webtoons = MangaRepository.getWebtoons()

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        AppHeader(navigator)

        MangaSection(
            titreSection = "Populaires",
            mangas = mangasPopulaires,
            navigator = navigator
        )

        MangaSection(
            titreSection = "Webtoons",
            mangas = webtoons,
            navigator = navigator
        )
    }
}
