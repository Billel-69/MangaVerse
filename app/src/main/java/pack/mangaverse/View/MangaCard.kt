package pack.mangaverse.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pack.mangaverse.R
import pack.mangaverse.data.Contenu

@Composable
fun MangaCard(contenu: Contenu, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(150.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.DarkGray)
    ) {
        AsyncImage(
            model = "https://m.media-amazon.com/images/I/81jS951SgDL.jpg"/*contenu.coverPath mettre sa si les liens fonctionnent*/ ,
            contentDescription = contenu.titre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            placeholder = painterResource(id = R.drawable.mangaverse),
            error = painterResource(id =R.drawable.mangaverse)
        )
    }
}