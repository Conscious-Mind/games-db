package com.davidson.gamesdb.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.davidson.gamesdb.domain.DomainGameGist
import com.davidson.gamesdb.network.RawgNetwork
import com.davidson.gamesdb.pagination.GamePagingSource

class GamesRepository() {


    init {

    }

//    private fun refreshGamesListInRepo() {
//        CoroutineScope(Dispatchers.IO).launch {
//            _games.postValue(database.GamesDao.getAllGamesFromDb())
//        }
//    }

//    suspend fun getGamesPaged(query: String, limit: Int, offset: Int) =
//        withContext(Dispatchers.IO) {
//            database.GamesDao.getAllGamesFromDbInPaged(query, limit, offset)
//        }


    fun getGamesListInPagedFromNetwork(
        query: String,
        maxPagesToGet: Int,
        platformToGet: Int,
        orderBy: String,
    ): LiveData<PagingData<DomainGameGist>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                GamePagingSource(RawgNetwork.retrofitRawgNetworkService, query, maxPagesToGet, platformToGet, orderBy)
            }).liveData
    }

//    suspend fun getGamesListFromNetwork(pageNumber: Int) {
//        withContext(Dispatchers.IO) {
//            try {
//                val gamesFromNetwork =
//                    RawgNetwork.retrofitRawgNetworkService.getAllGamesFromNetwork(pageNumber = pageNumber)
//
////                database.GamesDao.insertAll(gamesFromNetwork.results.asDatabaseModel())
////                refreshGamesListInRepo()
//            } catch (e: Exception) {
//                Log.e("ERROR_REPO", e.message.toString())
//            }
//        }
//    }


}