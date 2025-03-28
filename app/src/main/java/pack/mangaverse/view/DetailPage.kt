package pack.mangaverse.view

import AppHeader
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pack.mangaverse.data.models.MediaDataM

val lightBackgroundColor = Color(0xFF1F1F1F)
val lighterGray = Color(0xFFB0B0B0)

@Destination
@Composable
fun DetailPage(media: MediaDataM, navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader(navigator)
        Image(
            painter = rememberAsyncImagePainter(media.bannerImage),
            contentDescription = "Bannière",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = media.title ?: "inconnu",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(media.type, media.format, media.status).forEach {
                AssistChip(
                    onClick = {},
                    label = { Text(it.toString(), color = lighterGray) },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Synopsis", color = Color.White, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        media.description?.let { Text(it, color = Color.LightGray) }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Personnages", color = Color.White, fontWeight = FontWeight.SemiBold)
        media.characters.forEach { personnageRole ->
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightBackgroundColor, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(personnageRole.personnage?.image),
                        contentDescription = personnageRole.personnage?.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        personnageRole.personnage?.name?.let { Text(it, color = Color.White) }
                        personnageRole.role?.let {
                            Text(it, color = Color(0xFF9B4DFF))
                        }
                        personnageRole.personnage?.description?.let { Text(it, color = Color.LightGray, style = MaterialTheme.typography.bodySmall) }
                    }
                }
            }
        }
    }
}
