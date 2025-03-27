package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Personnage(
    var id: String,
    val nom: String,
    val imagePath: String,
    val description: String
) : Parcelable
