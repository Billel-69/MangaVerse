package pack.mangaverse.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonnageRole(
    val personnage: Personnage,
    val role: String
) : Parcelable
