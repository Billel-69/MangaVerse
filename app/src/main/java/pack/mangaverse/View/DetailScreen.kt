package pack.mangaverse.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import pack.mangaverse.data.Contenu

@Destination
@Composable
fun DetailScreen(contenu: Contenu) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Banner
        Image(
            painter = rememberAsyncImagePainter(contenu.bannerPath),
            contentDescription = "Bannière",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Titre
        Text(
            text = contenu.titre,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tags
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(contenu.type, contenu.format, contenu.status).forEach {
                AssistChip(
                    onClick = {},
                    label = { Text(it) },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text("Synopsis", color = Color.White, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(contenu.description, color = Color.LightGray)

        Spacer(modifier = Modifier.height(16.dp))

        // Personnages
        Text("Personnages", color = Color.White, fontWeight = FontWeight.SemiBold)
        contenu.personnages.forEach { personnageRole ->
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(personnageRole.personnage.imagePath),
                    contentDescription = personnageRole.personnage.nom,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(personnageRole.personnage.nom, color = Color.White)
                    Text(personnageRole.role, color = Color.Gray)
                    Text(personnageRole.personnage.description, color = Color.LightGray, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
