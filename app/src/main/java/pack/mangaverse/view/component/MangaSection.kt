package pack.mangaverse.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import pack.mangaverse.data.models.Contenu
import pack.mangaverse.data.models.MediaDataM
import pack.mangaverse.destinations.DetailPageDestination

@Composable
fun MangaSection(
    titreSection: String,
    mangas: List<MediaDataM>,
    navigator: DestinationsNavigator
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
        Text(
            text = titreSection,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                mangas.forEach { contenu ->
                    MangaCard(
                        MediaData = contenu,
                        modifier = Modifier
                            .clickable { navigator.navigate(DetailPageDestination(media = contenu)) }
                    )
                }
            }

            // Flèche droite
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(scrollState.value + 300)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .size(36.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black.copy(alpha = 0.5f))
            ) {
                Text("▶", color = Color.White)
            }

            // Flèche gauche
            if (scrollState.value > 0) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(scrollState.value - 300)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .size(36.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black.copy(alpha = 0.5f))
                ) {
                    Text("◀", color = Color.White)
                }
            }
        }
    }
}
