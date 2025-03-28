package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Personnage(
    var id: Int,
    val name: String,
    val image: String,
    val description: String?
): Parcelable
