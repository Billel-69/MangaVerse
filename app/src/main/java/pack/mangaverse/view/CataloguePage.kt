import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.data.MangaVerseViewModel
import pack.mangaverse.data.models.MediaData
import pack.mangaverse.destinations.DetailPageDestination
import pack.mangaverse.view.component.MangaCard

@Destination
@Composable
fun CataloguePage(navigator: DestinationsNavigator) {
    val viewModel: MangaVerseViewModel = viewModel()
    val mediaList by viewModel.mediaList.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredMedia = mediaList.filter {
        it.title.toString().contains(searchQuery.text, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        AppHeader(navigator)

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Rechercher un média") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.Gray
            )
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            items(filteredMedia) { media ->
                Column {
                    Row(
                        modifier = Modifier
                            .clickable {
                                navigator.navigate(DetailPageDestination(media = media))
                            }

                    ){
                        MangaCard(media)

                    }
                    Row {
                        Text(
                            text = media.title.toString(),
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
