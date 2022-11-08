package com.davidson.gamesdb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.davidson.gamesdb.repository.GamesRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val gamesRepo = GamesRepository()

    val gamesList = gamesRepo.getGamesListInPagedFromNetwork("")


    init {

    }
}