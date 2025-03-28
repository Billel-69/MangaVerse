package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateDebut(
    val year: Int?,
    val month: Int?,
    val day: Int?
) : Parcelable
