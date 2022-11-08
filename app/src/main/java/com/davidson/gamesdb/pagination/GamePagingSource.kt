package com.davidson.gamesdb.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidson.gamesdb.domain.DomainGame
import com.davidson.gamesdb.network.RawgGamesNetworkService
import com.davidson.gamesdb.network.asDomainModel
import kotlinx.coroutines.delay

class GamePagingSource(
    private val apiService: RawgGamesNetworkService,
    val searchQuery: String = ""
) :
    PagingSource<Int, DomainGame>() {
    override fun getRefreshKey(state: PagingState<Int, DomainGame>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainGame> {
        Log.e("PG_SRC", " Loading Data Step 1")
        val page = params.key ?: 1

        return try {
            val response =
                apiService.getAllGamesFromNetwork(pageNumber = page, searchQuery = searchQuery)
            Log.e("PG_SRC", " Loading Data Step ")

            return if (response.isSuccessful) {
                Log.e("PG_SRC", response.body()?.results?.size.toString() + " Loading Data")

                val results = response.body()?.results

                return if (results != null) {
                    LoadResult.Page(
                        data = results.asDomainModel(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (results.isEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Page(
                        data = listOf<DomainGame>(),
                        prevKey = page - 1,
                        nextKey = null
                    )
                }

            } else {
                LoadResult.Error(Exception("Error in response"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }



    }
}