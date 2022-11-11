package com.davidson.gamesdb.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL_RAWG_API = "https://api.rawg.io/"

const val API_KEY = "d57042316ebf43d996b5f6427c117c37"

const val DEFAULT_ORDER_BY = ""
const val DEFAULT_PAGE_NUMBER = 1
const val DEFAULT_PAGE_SIZE = 10
const val DEFAULT_SEARCH_QUERY = ""
const val DEFAULT_PLATFORM = 4


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitRawgGames = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_RAWG_API)
    .build()

interface RawgGamesNetworkService {
    @GET("api/games")
    suspend fun getAllGamesFromNetwork(
        @Query("key")
        apiKey: String = API_KEY,
        @Query("ordering")
        orderBy: String = DEFAULT_ORDER_BY,
        @Query("platforms")
        platform: Int = DEFAULT_PLATFORM,
        @Query("page")
        pageNumber: Int = DEFAULT_PAGE_NUMBER,
        @Query("page_size")
        pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("search")
        searchQuery: String = DEFAULT_SEARCH_QUERY
    ): Response<NetworkGameGistResponse>
}

object RawgNetwork {
    val retrofitRawgNetworkService: RawgGamesNetworkService by lazy {
        retrofitRawgGames.create(
            RawgGamesNetworkService::class.java
        )
    }
}

