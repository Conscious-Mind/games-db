package com.davidson.gamesdb.network


import com.davidson.gamesdb.database.DatabaseGame
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Test(
    @Json(name = "count")
    val count: Int, // 820987
    @Json(name = "next")
    val next: String?, // https://api.rawg.io/api/games?key=d57042316ebf43d996b5f6427c117c37&page=2
    @Json(name = "previous")
    val previous: String?, // null
    @Json(name = "results")
    val results: List<Result>,
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "background_image")
        val backgroundImage: String, // https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg
        @Json(name = "dominant_color")
        val dominantColor: String, // 0f0f0f
        @Json(name = "genres")
        val genres: List<Genre>,
        @Json(name = "id")
        val id: Int, // 3498
        @Json(name = "metacritic")
        val metacritic: Float, // 91
        @Json(name = "name")
        val name: String, // Grand Theft Auto V
        @Json(name = "platforms")
        val platforms: List<Platform>,
        @Json(name = "rating")
        val rating: Float, // 4.47
        @Json(name = "released")
        val released: String, // 2013-09-17
        @Json(name = "saturated_color")
        val saturatedColor: String, // 0f0f0f
        @Json(name = "short_screenshots")
        val shortScreenshots: List<ShortScreenshot>,
        @Json(name = "tba")
        val tba: Boolean, // false
        @Json(name = "updated")
        val updated: String, // 2022-11-05T15:53:53
    ) {
        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "games_count")
            val gamesCount: Int, // 166200
            @Json(name = "id")
            val id: Int, // 4
            @Json(name = "image_background")
            val imageBackground: String, // https://media.rawg.io/media/games/4be/4be6a6ad0364751a96229c56bf69be59.jpg
            @Json(name = "name")
            val name: String, // Action
            @Json(name = "slug")
            val slug: String // action
        )

        @JsonClass(generateAdapter = true)
        data class Platform(
            @Json(name = "platform")
            val platformString: PlatformString,
            @Json(name = "released_at")
            val releasedAt: String?, // 2013-09-17
        ) {
            @JsonClass(generateAdapter = true)
            data class PlatformString(
                @Json(name = "image_background")
                val imageBackground: String, // https://media.rawg.io/media/games/7fa/7fa0b586293c5861ee32490e953a4996.jpg
                @Json(name = "name")
                val name: String, // PC
            )
        }

        @JsonClass(generateAdapter = true)
        data class ShortScreenshot(
            @Json(name = "id")
            val id: Int, // -1
            @Json(name = "image")
            val image: String // https://media.rawg.io/media/games/456/456dea5e1c7e3cd07060c14e96612001.jpg
        )
    }

}


fun List<Test.Result>.asDatabaseModel(): List<DatabaseGame> {
    return map {
        DatabaseGame(
            gameId = it.id,
            gameName = it.name,
            gameBgImage = it.backgroundImage,
            gameMetaCritic = it.metacritic,
            gameRating = it.rating,
            gameReleaseDate = it.released,
            gameUpdatedDate = it.updated,
            gameTba = it.tba,
            gameDominantColor = it.dominantColor,
            gameSaturatedColor = it.saturatedColor
        )
    }
}
