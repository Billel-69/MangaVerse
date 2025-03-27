package pack.mangaverse.data

import pack.mangaverse.data.models.Contenu
import pack.mangaverse.data.models.DateDebut

object MangaRepository {

    fun getMangasPopulaires(): List<Contenu> = listOf(
        Contenu(
            id = "1",
            titre = "Swordmaster's Youngest Son",
            format = "Manhwa",
            type = "Action",
            status = "En cours",
            description = "Jin Runcandel est le plus jeune fils du plus grand maître épéiste...",
            dateDebut = DateDebut(2022, 11, 1),
            nbEpisodes = 95,
            sources = "https://example.com",
            coverPath = "https://m.media-amazon.com/images/I/81eA+K3rZ8L.jpg",
            bannerPath = "https://i.pinimg.com/originals/45/61/72/45617286f1a2aabfb9ae8e2c2e2cf1aa.jpg",
            personnages = emptyList()
        ),
        Contenu(
            id = "2",
            titre = "Return of the Mount Hua Sect",
            format = "Manhwa",
            type = "Arts Martiaux",
            status = "En cours",
            description = "Un maître légendaire renaît dans un corps jeune pour se venger.",
            dateDebut = DateDebut(2021, 5, 12),
            nbEpisodes = 130,
            sources = "https://example.com",
            coverPath = "https://m.media-amazon.com/images/I/81CZXq6EUBL.jpg",
            bannerPath = "https://i.pinimg.com/originals/1a/59/95/1a5995e8b6717d72604c221272cc00f3.jpg",
            personnages = emptyList()
        ),
        Contenu(
            id = "2",
            titre = "Return of the Disaster-Class Hero",
            format = "Manhwa",
            type = "Fantastique",
            status = "En cours",
            description = "Un héros trahi revient d'entre les morts pour se venger et sauver le monde.",
            dateDebut = DateDebut(2020, 3, 18),
            nbEpisodes = 85,
            sources = "https://example.com",
            coverPath = "https://m.media-amazon.com/images/I/61BtzM5Oc-L.jpg",
            bannerPath = "https://i.pinimg.com/originals/1d/7f/60/1d7f60188f41d88053ac993f557db631.jpg",
            personnages = emptyList()
        ),
        Contenu(
            id = "2",
            titre = "Pick Me Up, Infinite Gacha",
            format = "Webtoon",
            type = "Aventure",
            status = "En cours",
            description = "Un joueur se retrouve coincé dans un jeu de gacha infini et doit survivre.",
            dateDebut = DateDebut(2021, 10, 10),
            nbEpisodes = 72,
            sources = "https://example.com",
            coverPath = "https://m.media-amazon.com/images/I/81V9KhXECvL.jpg",
            bannerPath = "https://i.pinimg.com/originals/b2/77/15/b277154fa15282bb90c30c4bc23c24ff.jpg",
            personnages = emptyList()
        )
    )

    fun getWebtoons(): List<Contenu> = listOf(
        Contenu(
            id = "2",
            titre = "Eleceed",
            format = "Webtoon",
            type = "Action",
            status = "En cours",
            description = "Un lycéen au cœur pur fusionne avec un chat et découvre des pouvoirs cachés.",
            dateDebut = DateDebut(2019, 9, 25),
            nbEpisodes = 200,
            sources = "https://example.com",
            coverPath = "https://m.media-amazon.com/images/I/81g-j0B8vzL.jpg",
            bannerPath = "https://i.pinimg.com/originals/8d/96/33/8d9633e10b4dcf0c0dbccf259302d2b0.jpg",
            personnages = emptyList()
        ),
        Contenu(
            id = "2",
            titre = "Omniscient Reader",
            format = "Webtoon",
            type = "Fantaisie",
            status = "En cours",
            description = "Un lecteur devient le héros de son roman favori dans un monde apocalyptique.",
            dateDebut = DateDebut(2020, 6, 3),
            nbEpisodes = 150,
            sources = "https://example.com",
            coverPath = "https://m.media-amazon.com/images/I/81B1FB0ML-L.jpg",
            bannerPath = "https://i.pinimg.com/originals/27/f7/72/27f7727e29a535b9888803704d56c5ff.jpg",
            personnages = emptyList()
        )
    )
}
