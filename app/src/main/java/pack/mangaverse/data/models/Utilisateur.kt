package pack.mangaverse.data.models
import java.util.Date

data class Utilisateur(
    val uid: String,
    var id: String,
    val pseudo: String,
    val email: String,
    val mdp: String,
    val pdp: String,
    val dateCreation: Date,
    val favoris: List<Contenu>
)