package pack.mangaverse.data.models

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaDataM(
    @get:PropertyName("id") val mediaId: String = "",
    @get:PropertyName("titre") val title: String? = null,
    @get:PropertyName("format") val format: String? = null,
    @get:PropertyName("type") val type: String? = null,
    @get:PropertyName("status") val status: String? = null,
    @get:PropertyName("description") val description: String? = null,
    @get:PropertyName("dateDebut") val startDate: DateDebutM? = null,
    @get:PropertyName("nbEpisodes") val episodes: Int? = null,
    @get:PropertyName("sources") val source: String? = null,
    @get:PropertyName("coverPath") val coverImage: String? = null,
    @get:PropertyName("bannerPath") val bannerImage: String? = null,
    @get:PropertyName("personnages") val characters: List<PersonnageM> = emptyList(),
    @get:PropertyName("genres") val genres: List<String> = emptyList()
) : Parcelable

@Parcelize
data class PersonnageM(
    @get:PropertyName("role") val role: String? = null,
    @get:PropertyName("personnage") val personnage: PersonnageDetailsM? = null
) : Parcelable

@Parcelize
data class PersonnageDetailsM(
    @get:PropertyName("image") val image: String? = null,
    @get:PropertyName("name") val name: String? = null,
    @get:PropertyName("description") val description: String? = null,
    @get:PropertyName("id") val id: String? = null
) : Parcelable

@Parcelize
data class DateDebutM(
    @get:PropertyName("year") val year: Int = 0,
    @get:PropertyName("month") val month: Int = 0,
    @get:PropertyName("day") val day: Int = 0,
    @get:PropertyName("stability") val stability: Int = 0
) : Parcelable
