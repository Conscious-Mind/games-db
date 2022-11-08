//package com.davidson.gamesdb.database
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.davidson.gamesdb.domain.DomainGame
//
//@Entity(tableName = "table_games_all")
//data class DatabaseGame constructor(
//    @PrimaryKey(autoGenerate = false)
//    val gameId: Int,
//    val gameName: String,
//    val gameBgImage: String,
//    val gameMetaCritic: Float,
//    val gameRating: Float,
//    val gameReleaseDate: String,
//    val gameSaturatedColor: String,
//    val gameDominantColor: String,
//    val gameTba: Boolean,
//    val gameUpdatedDate: String
//)
//
//fun List<DatabaseGame>.asDomainModel(): List<DomainGame> {
//    return map {
//        DomainGame(
//            gameId = it.gameId,
//            gameName = it.gameName,
//            gameBgImage = it.gameBgImage,
//            gameMetaCritic = it.gameMetaCritic,
//            gameRating = it.gameRating,
//            gameReleaseDate = it.gameReleaseDate,
//            gameUpdatedDate = it.gameUpdatedDate,
//            gameTba = it.gameTba,
//            gameDominantColor = it.gameDominantColor,
//            gameSaturatedColor = it.gameSaturatedColor
//        )
//    }
//}
//
//fun DatabaseGame.asDomainModel(): DomainGame {
//    return DomainGame(
//        gameId = gameId,
//        gameName = gameName,
//        gameBgImage = gameBgImage,
//        gameMetaCritic = gameMetaCritic,
//        gameRating = gameRating,
//        gameReleaseDate = gameReleaseDate,
//        gameUpdatedDate = gameUpdatedDate,
//        gameTba = gameTba,
//        gameDominantColor = gameDominantColor,
//        gameSaturatedColor = gameSaturatedColor
//    )
//}