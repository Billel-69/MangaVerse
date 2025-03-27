package pack.mangaverse.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Utilisateur(
    val id: String,
    val pseudo: String,
    val email: String,
    val mdp: String,
    val pdp: String,
    val dateCreation: Date,
    val favoris: List<Contenu>
) : Parcelable
