import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CatalogueScreen() {
    Text(text = "Catalogue")
}

@Composable
fun CatalogueScreenPreview() {
    CatalogueScreen()
}
