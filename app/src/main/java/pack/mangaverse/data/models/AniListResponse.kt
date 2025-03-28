package pack.mangaverse.data.models

data class AniListResponse(
    val data: Data
)

data class Data(
    val Media: MediaData
)

data class MediaData(
    val id: Int,
    val title: Title,
    val format: String?,
    val type: String?,
    val status: String?,
    val description: String?,
    val startDate: DateDebut?,
    val episodes: Int?,
    val source: String?,
    val coverImage: CoverImage,
    val bannerImage: String?,
    val characters: Characters,
    val genres: List<String>
)

data class Title(
    val romaji: String
)

data class CoverImage(
    val extraLarge: String
)

data class Characters(
    val edges: List<CharacterEdge>
)

data class CharacterEdge(
    val node: CharacterNode,
    val role: String
)

data class CharacterNode(
    val id: Int,
    val name: Name,
    val image: Image,
    val description: String?
)

data class Name(
    val full: String
)

data class Image(
    val large: String
)
