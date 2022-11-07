package com.davidson.gamesdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.davidson.gamesdb.database.getDatabase
import com.davidson.gamesdb.network.RawgGamesNetworkService
import com.davidson.gamesdb.network.RawgNetwork
import com.davidson.gamesdb.repository.GamesRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val gamesRepo = GamesRepository(getDatabase(application))

    val gamesList = gamesRepo.getGamesListInPagedFromDb("")

    init {
        viewModelScope.launch {
            gamesRepo.getGamesListFromNetwork(1)
        }

    }
}