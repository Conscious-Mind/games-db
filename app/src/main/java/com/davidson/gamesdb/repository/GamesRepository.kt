package com.davidson.gamesdb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.davidson.gamesdb.database.DatabaseGame
import com.davidson.gamesdb.database.GamesListDatabase
import com.davidson.gamesdb.database.asDomainModel
import com.davidson.gamesdb.domain.DomainGame
import com.davidson.gamesdb.network.RawgNetwork
import com.davidson.gamesdb.network.asDatabaseModel
import com.davidson.gamesdb.pagination.GamePagingSource
import kotlinx.coroutines.*

class GamesRepository(private val database: GamesListDatabase) {

    private val _games = MutableLiveData<List<DatabaseGame>>()
    val games: LiveData<List<DomainGame>>
        get() = Transformations.map(_games) {
            it.asDomainModel()
        }

    init {

    }

    private fun refreshGamesListInRepo() {
        CoroutineScope(Dispatchers.IO).launch {
            _games.postValue(database.GamesDao.getAllGamesFromDb())
        }
    }

    suspend fun getGamesPaged(query: String, limit: Int, offset: Int) =
        withContext(Dispatchers.IO) {
            database.GamesDao.getAllGamesFromDbInPaged(query, limit, offset)
        }


    fun getGamesListInPagedFromDb(query:String):LiveData<PagingData<DatabaseGame>>{
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                enablePlaceholders = false,
                initialLoadSize = 4
            ),
            pagingSourceFactory = {
                GamePagingSource(this,query)
            }).liveData
    }

    suspend fun getGamesListFromNetwork(pageNumber: Int) {
        withContext(Dispatchers.IO) {
            try {
                val gamesFromNetwork =
                    RawgNetwork.retrofitRawgNetworkService.getAllGamesFromNetwork(pageNumber = pageNumber)

                database.GamesDao.insertAll(gamesFromNetwork.results.asDatabaseModel())
                refreshGamesListInRepo()
            } catch (e: Exception) {
                Log.e("ERROR_REPO", e.message.toString())
            }
        }
    }
}