package pack.mangaverse.data

import com.google.firebase.firestore.FirebaseFirestore
import pack.mangaverse.data.models.Contenu
import pack.mangaverse.data.models.Genre
import pack.mangaverse.data.models.Personnage
import pack.mangaverse.data.models.Utilisateur

class DataFactory(private val db: FirebaseFirestore) {

    private val utilisateurCollection = db.collection("utilisateurs")
    private val genreCollection = db.collection("genres")
    private val contenuCollection = db.collection("contenus")
    private val personnageCollection = db.collection("personnages")

    // ------------------------ UTILISATEUR ------------------------
    fun addUtilisateur(utilisateur: Utilisateur, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        utilisateurCollection.document(utilisateur.id).set(utilisateur)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun removeUtilisateur(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        utilisateurCollection.document(id).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getUtilisateur(id: String, onSuccess: (Utilisateur) -> Unit, onFailure: (Exception) -> Unit) {
        utilisateurCollection.document(id).get()
            .addOnSuccessListener { document ->
                val utilisateur = document.toObject(Utilisateur::class.java)
                utilisateur?.id = document.id
                utilisateur?.let { onSuccess(it) } ?: onFailure(Exception("Utilisateur not found"))
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getAllUtilisateurs(onSuccess: (List<Utilisateur>) -> Unit, onFailure: (Exception) -> Unit) {
        utilisateurCollection.get()
            .addOnSuccessListener { result ->
                val users = result.documents.mapNotNull { it.toObject(Utilisateur::class.java) }
                onSuccess(users)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // ------------------------ GENRE ------------------------
    fun addGenre(genre: Genre, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        genreCollection.document(genre.id).set(genre)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun removeGenre(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        genreCollection.document(id).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getGenre(id: String, onSuccess: (Genre) -> Unit, onFailure: (Exception) -> Unit) {
        genreCollection.document(id).get()
            .addOnSuccessListener { document ->
                val genre = document.toObject(Genre::class.java)
                genre?.id = document.id
                genre?.let { onSuccess(it) } ?: onFailure(Exception("Genre not found"))
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getAllGenres(onSuccess: (List<Genre>) -> Unit, onFailure: (Exception) -> Unit) {
        genreCollection.get()
            .addOnSuccessListener { result ->
                val genres = result.documents.mapNotNull { it.toObject(Genre::class.java) }
                onSuccess(genres)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // ------------------------ CONTENU ------------------------
    fun addContenu(contenu: Contenu, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        contenuCollection.document(contenu.id).set(contenu)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun removeContenu(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        contenuCollection.document(id).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getContenu(id: String, onSuccess: (Contenu) -> Unit, onFailure: (Exception) -> Unit) {
        contenuCollection.document(id).get()
            .addOnSuccessListener { document ->
                val contenu = document.toObject(Contenu::class.java)
                contenu?.id = document.id
                contenu?.let { onSuccess(it) } ?: onFailure(Exception("Contenu not found"))
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getAllContenus(onSuccess: (List<Contenu>) -> Unit, onFailure: (Exception) -> Unit) {
        contenuCollection.get()
            .addOnSuccessListener { result ->
                val contenus = result.documents.mapNotNull { it.toObject(Contenu::class.java) }
                onSuccess(contenus)
            }
            .addOnFailureListener { onFailure(it) }
    }

    // ------------------------ PERSONNAGE ------------------------
    fun addPersonnage(personnage: Personnage, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        personnageCollection.document(personnage.id).set(personnage)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun removePersonnage(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        personnageCollection.document(id).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun getPersonnage(id: String, onSuccess: (Personnage) -> Unit, onFailure: (Exception) -> Unit) {
        personnageCollection.document(id).get()
            .addOnSuccessListener { document ->
                val personnage = document.toObject(Personnage::class.java)
                personnage?.id = document.id
                personnage?.let { onSuccess(it) } ?: onFailure(Exception("Personnage not found"))
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getAllPersonnages(onSuccess: (List<Personnage>) -> Unit, onFailure: (Exception) -> Unit) {
        personnageCollection.get()
            .addOnSuccessListener { result ->
                val personnages = result.documents.mapNotNull { it.toObject(Personnage::class.java) }
                onSuccess(personnages)
            }
            .addOnFailureListener { onFailure(it) }
    }
}
