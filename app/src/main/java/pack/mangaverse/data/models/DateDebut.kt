package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateDebut(
    val annee: Int,
    val mois: Int,
    val jour: Int
) : Parcelable
