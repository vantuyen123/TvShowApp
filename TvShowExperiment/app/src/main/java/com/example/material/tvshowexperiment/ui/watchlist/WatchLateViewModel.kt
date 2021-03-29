package com.example.material.tvshowexperiment.ui.watchlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.material.tvshowexperiment.db.TvShowDao
import com.example.material.tvshowexperiment.db.TvShowDatabase

class WatchLateViewModel(
    private val database: TvShowDao,
    application: Application
) : AndroidViewModel(application) {

    val movie = database.getWatchList()

}