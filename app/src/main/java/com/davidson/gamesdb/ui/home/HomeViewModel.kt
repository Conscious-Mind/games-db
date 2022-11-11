package com.davidson.gamesdb.ui.home

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidson.gamesdb.domain.DomainGameGist
import com.davidson.gamesdb.repository.GamesRepository
import com.davidson.gamesdb.util.Constants

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val gamesRepo = GamesRepository()

    var gameFromPc = getWhatIWant( maxPages = 3, platform = Constants.PLATFORM_PC, orderBy = "-rating").cachedIn(viewModelScope)
    var gameFromPs5 = getWhatIWant( maxPages = 3, platform = Constants.PLATFORM_PS5, orderBy = "-rating").cachedIn(viewModelScope)
    var gameFromMobile = getWhatIWant( maxPages = 3, platform = Constants.PLATFORM_ANDROID, orderBy = "-rating").cachedIn(viewModelScope)


    init {

    }

    private fun getWhatIWant(queryString: String = "", maxPages: Int, platform: Int, orderBy: String = ""): LiveData<PagingData<DomainGameGist>> {
        return gamesRepo.getGamesListInPagedFromNetwork(queryString, maxPages, platform, orderBy)
    }
}