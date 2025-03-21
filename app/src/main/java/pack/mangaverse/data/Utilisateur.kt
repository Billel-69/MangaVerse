package pack.mangaverse.data
import java.util.Date

data class Utilisateur(
    val id: String,
    val pseudo: String,
    val email: String,
    val mdp: String,
    val pdp: String,
    val dateCreation: Date,
    val favoris: List<Contenu>
)