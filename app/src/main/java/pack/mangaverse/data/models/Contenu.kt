package pack.mangaverse.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contenu(
    var id: String,
    val genres: List<String>,
    val titre: String,
    val format: String,
    val type: String,
    val status: String,
    val description: String,
    val dateDebut: DateDebut,
    val nbEpisodes: Int,
    val sources: String,
    val coverPath: String,
    val bannerPath: String,
    val personnages: List<PersonnageRole>
) : Parcelable
