package com.davidson.gamesdb.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class DomainGameGist(
    val gameGistId: Int,
    val gameGistName: String,
    val gameGistBgImage: String,
    val gameGistRating: Float,
    val gameGistScreenshot1: String,
    val gameGistScreenshot2: String,
    val gameGistScreenshot3: String,
) : Parcelable