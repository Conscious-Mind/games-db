package com.davidson.gamesdb.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidson.gamesdb.domain.DomainGame
import com.davidson.gamesdb.network.RawgGamesNetworkService
import com.davidson.gamesdb.network.asDomainModel

class GamePagingSource(
    private val apiService: RawgGamesNetworkService,
    private val serQuery: String = "",
    private val maxPagesToGet: Int = 4,
) :
    PagingSource<Int, DomainGame>() {
    override fun getRefreshKey(state: PagingState<Int, DomainGame>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainGame> {
        val page = params.key ?: 1
        var loadResult: LoadResult<Int, DomainGame>? = null
        Log.e("loadState", "step 1")

        if (serQuery == "returnEmpty#21") {
            return LoadResult.Page(
                data = listOf<DomainGame>(),
                prevKey = null,
                nextKey = null
            )
        }

        try {
            val response =
                apiService.getAllGamesFromNetwork(
                    pageNumber = page,
                    searchQuery = serQuery,
                    pageSize = params.loadSize
                )

            Log.e("loadState", response.errorBody().toString())
            println(response.errorBody().toString())

            if (response.isSuccessful) {
                Log.e("loadState", response.body()?.results?.size.toString() + " Loading Data")

                val results = response.body()?.results

                loadResult = if (results != null) {
                    LoadResult.Page(
                        data = results.asDomainModel(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page == maxPagesToGet) null else page + 1
                    )
                } else {
                    LoadResult.Page(
                        data = listOf<DomainGame>(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = null
                    )
                }

            } else {
                loadResult = LoadResult.Error(Exception("Error in response"))
            }
        } catch (e: Exception) {
            Log.e("loadState", "step 2")
            println(e.message)

            loadResult = LoadResult.Error(e)
        }

        return loadResult ?: LoadResult.Error(Exception("no response"))

    }
}