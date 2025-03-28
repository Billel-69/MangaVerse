package pack.mangaverse.data.models


data class Utilisateur(
    val uid: String = "",
    var pseudo: String = "",
    val pdp: String = "",
    val favoris: List<String> = emptyList()
) {
    constructor() : this("", "", "", emptyList())
}