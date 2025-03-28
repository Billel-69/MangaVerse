package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre (
    var id: String,
    val nom: String
):Parcelable