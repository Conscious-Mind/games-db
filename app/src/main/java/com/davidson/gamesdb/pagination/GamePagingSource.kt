package com.davidson.gamesdb.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidson.gamesdb.domain.DomainGameGist
import com.davidson.gamesdb.network.RawgGamesNetworkService
import com.davidson.gamesdb.network.asDomainModelGist

class GamePagingSource(
    private val apiService: RawgGamesNetworkService,
    private val serQuery: String = "",
    private val maxPagesToGet: Int = 4,
    private val filterPlatform: Int = 4,
    private val orderBy: String = "",
) :
    PagingSource<Int, DomainGameGist>() {
    override fun getRefreshKey(state: PagingState<Int, DomainGameGist>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainGameGist> {
        val page = params.key ?: 1
        var loadResult: LoadResult<Int, DomainGameGist>?
        Log.e("loadState", "step 1")

        if (serQuery == "returnEmpty#21") {
            return LoadResult.Page(
                data = listOf<DomainGameGist>(),
                prevKey = null,
                nextKey = null
            )
        }

        try {
            val response =
                apiService.getAllGamesFromNetwork(
                    pageNumber = page,
                    searchQuery = serQuery,
                    pageSize = params.loadSize,
                    platform = filterPlatform,
                    orderBy = orderBy,
                )

            println("step 3")

            Log.e("loadStateErr", response.errorBody().toString())
            println(response.errorBody().toString())

            if (response.isSuccessful) {
                Log.e(
                    "loadState",
                    response.body()?.listOfNetworkGameGist?.size.toString() + " Loading Data"
                )

                val results = response.body()?.listOfNetworkGameGist

                loadResult = if (results != null) {
                    LoadResult.Page(
                        data = results.asDomainModelGist(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page == maxPagesToGet) null else page + 1
                    )
                } else {
                    LoadResult.Page(
                        data = listOf<DomainGameGist>(),
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