package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Utilisateur(
    val uid: String,
    var id: String,
    val pseudo: String,
    val email: String,
    val mdp: String,
    val pdp: String,
    val dateCreation: Date,
    val favoris: List<Contenu>
) : Parcelable
