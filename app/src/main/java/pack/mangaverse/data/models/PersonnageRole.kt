package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonnageRole(
    val personnage: Personnage,
    val role: String
) : Parcelable
