package com.davidson.gamesdb.network


import com.davidson.gamesdb.domain.DomainGame
import com.davidson.gamesdb.domain.DomainGameGist
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Test(
    @Json(name = "count") val count: Int?, // 820987
    @Json(name = "next") val next: String?, // https://api.rawg.io/api/games?key=d57042316ebf43d996b5f6427c117c37&page=2
    @Json(name = "previous") val previous: String?, // null
    @Json(name = "results") val results: List<Result>,
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "background_image") val backgroundImage: String?, // https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg
        @Json(name = "dominant_color") val dominantColor: String?, // 0f0f0f
        @Json(name = "genres") val genres: List<Genre>,
        @Json(name = "id") val id: Int, // 3498
        @Json(name = "metacritic") val metacritic: Float?, // 91
        @Json(name = "name") val name: String?, // Grand Theft Auto V
        @Json(name = "platforms") val platforms: List<Platform>,
        @Json(name = "rating") val rating: Float?, // 4.47
        @Json(name = "released") val released: String?, // 2013-09-17
        @Json(name = "saturated_color") val saturatedColor: String?, // 0f0f0f
        @Json(name = "short_screenshots") val shortScreenshots: List<ShortScreenshot>,
        @Json(name = "tba") val tba: Boolean?, // false
        @Json(name = "updated") val updated: String?, // 2022-11-05T15:53:53
    ) {
        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "games_count") val gamesCount: Int?, // 166200
            @Json(name = "name") val name: String, // Action
        )

        @JsonClass(generateAdapter = true)
        data class Platform(
            @Json(name = "platform") val platformString: PlatformString,
            @Json(name = "released_at") val releasedAt: String?, // 2013-09-17
        ) {
            @JsonClass(generateAdapter = true)
            data class PlatformString(
                @Json(name = "name") val name: String, // PC
            )
        }

        @JsonClass(generateAdapter = true)
        data class ShortScreenshot(
            @Json(name = "image") val image: String // https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg
        )
    }

}


//fun List<Test.Result>.asDatabaseModel(): List<DatabaseGame> {
//    return map {
//        DatabaseGame(
//            gameId = it.id,
//            gameName = it.name ?: "Unavailable",
//            gameBgImage = it.backgroundImage ?: "Unavailable",
//            gameMetaCritic = it.metacritic ?: 0.0F,
//            gameRating = it.rating ?: 0.0F,
//            gameReleaseDate = it.released ?: "Unavailable",
//            gameUpdatedDate = it.updated ?: "Unavailable",
//            gameTba = it.tba ?: false,
//            gameDominantColor = it.dominantColor ?: "Unavailable",
//            gameSaturatedColor = it.saturatedColor ?: "Unavailable"
//        )
//    }
//}

fun List<Test.Result>.asDomainModel(): List<DomainGame> {
    return map {
        DomainGame(
            gameId = it.id,
            gameName = it.name ?: "Unavailable",
            gameBgImage = it.backgroundImage ?: "Unavailable",
            gameMetaCritic = it.metacritic ?: 0.0F,
            gameRating = it.rating ?: 0.0F,
            gameReleaseDate = it.released ?: "Unavailable",
            gameUpdatedDate = it.updated ?: "Unavailable",
            gameTba = it.tba ?: false,
            gameDominantColor = it.dominantColor ?: "Unavailable",
            gameSaturatedColor = it.saturatedColor ?: "Unavailable"
        )
    }
}

@JsonClass(generateAdapter = true)
data class NetworkGameGistResponse(
    @Json(name = "results") val listOfNetworkGameGist: List<NetworkGameGist>
) {
    @JsonClass(generateAdapter = true)
    class NetworkGameGist(
        @Json(name = "id") val networkGameGistId: Int, // 3498
        @Json(name = "rating") val networkGameGistRating: Float?, // 4.47
        @Json(name = "name") val networkGameGistName: String?, // Grand Theft Auto V
        @Json(name = "background_image") val backgroundImage: String?, // https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg
    )
}

fun List<NetworkGameGistResponse.NetworkGameGist>.asDomainModelGist(): List<DomainGameGist> {
    return map {
        DomainGameGist(
            gameGistId = it.networkGameGistId,
            gameGistName = it.networkGameGistName ?: "Unavailable",
            gameGistBgImage = it.backgroundImage ?: "Unavailable",
            gameGistRating = it.networkGameGistRating ?: 0.0F,
            gameGistScreenshot1 = it.backgroundImage  ?: "Unavailable",
            gameGistScreenshot2 = it.backgroundImage  ?: "Unavailable",
            gameGistScreenshot3 = it.backgroundImage  ?: "Unavailable",
        )
    }
}