package com.davidson.gamesdb.domain

data class DomainGame(
    val gameId: Int,
    val gameName: String,
    val gameBgImage: String,
    val gameMetaCritic: Float,
    val gameRating: Float,
    val gameReleaseDate: String,
    val gameSaturatedColor: String,
    val gameDominantColor: String,
    val gameTba: Boolean,
    val gameUpdatedDate: String
)