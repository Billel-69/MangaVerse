package pack.mangaverse.data

import pack.mangaverse.data.models.Contenu
import pack.mangaverse.data.models.Genre
import pack.mangaverse.data.models.Personnage
import pack.mangaverse.data.models.Utilisateur

class DataManager(private val dataFactory: DataFactory) {

    private var utilisateursList: List<Utilisateur> = emptyList()
    private var genresList: List<Genre> = emptyList()
    private var contenusList: List<Contenu> = emptyList()
    private var personnagesList: List<Personnage> = emptyList()

    // Update all lists from Firestore
    fun updateAllLists(onComplete: () -> Unit, onFailure: (Exception) -> Unit) {
        dataFactory.getAllGenres(
            onSuccess = { genres ->
                genresList = genres
                dataFactory.getAllPersonnages(
                    onSuccess = { personnages ->
                        personnagesList = personnages
                        dataFactory.getAllContenus(
                            onSuccess = { contenus ->
                                contenusList = contenus
                                dataFactory.getAllUtilisateurs(
                                    onSuccess = { utilisateurs ->
                                        utilisateursList = utilisateurs
                                        onComplete()
                                    },
                                    onFailure = onFailure
                                )
                            },
                            onFailure = onFailure
                        )
                    },
                    onFailure = onFailure
                )
            },
            onFailure = onFailure
        )
    }


    fun getUtilisateurs(): List<Utilisateur> = utilisateursList
    fun deleteUtilisateur(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataFactory.removeUtilisateur(id, onSuccess, onFailure)
    }

    fun getGenres(): List<Genre> = genresList
    fun deleteGenre(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataFactory.removeGenre(id, onSuccess, onFailure)
    }

    fun getContenus(): List<Contenu> = contenusList
    fun deleteContenu(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataFactory.removeContenu(id, onSuccess, onFailure)
    }

    fun getPersonnages(): List<Personnage> = personnagesList
    fun deletePersonnage(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        dataFactory.removePersonnage(id, onSuccess, onFailure)
    }
}