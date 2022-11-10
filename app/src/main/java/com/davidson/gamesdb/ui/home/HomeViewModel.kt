package com.davidson.gamesdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.Glide.init
import com.davidson.gamesdb.domain.DomainGame
import com.davidson.gamesdb.repository.GamesRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val gamesRepo = GamesRepository()


    val searchQuery = MutableLiveData<String>()
    var gamesList = Transformations.switchMap(searchQuery){ getWhatIWant(it,3)}


    init {
        searchQuery.value = ""
    }

    fun getWhatIWant(queryString: String, maxPages: Int): LiveData<PagingData<DomainGame>> {
        return gamesRepo.getGamesListInPagedFromNetwork(queryString, maxPages).cachedIn(viewModelScope)
    }
}