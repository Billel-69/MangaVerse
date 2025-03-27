package pack.mangaverse.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Personnage(
    val nom: String,
    val imagePath: String,
    val description: String
) : Parcelable
