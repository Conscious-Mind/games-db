package com.davidson.gamesdb.pagination

import android.graphics.pdf.PdfDocument.PageInfo
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidson.gamesdb.database.DatabaseGame
import com.davidson.gamesdb.database.GamesDao
import com.davidson.gamesdb.network.RawgNetwork
import com.davidson.gamesdb.network.asDatabaseModel
import com.davidson.gamesdb.repository.GamesRepository
import kotlinx.coroutines.delay

class GamePagingSource(private val repository: GamesRepository, val searchQuery: String = "") :
    PagingSource<Int, DatabaseGame>() {
    override fun getRefreshKey(state: PagingState<Int, DatabaseGame>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DatabaseGame> {
        val page = params.key ?: 0

        return try {
            val entities = repository.getGamesPaged(searchQuery, params.loadSize, page * params.loadSize)
            Log.e("PG_SRC", entities.size.toString() + " Loading Data")

            if(page != 0)
            {
                delay(1000)
            }

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            Log.e("ERROR_PAGING_SOURCE", e.message.toString())
            LoadResult.Error(e)
        }
    }
}