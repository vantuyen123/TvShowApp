package com.example.material.tvshowexperiment.ui.watchlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.material.tvshowexperiment.db.TvShowDao

@Suppress("UNCHECKED_CAST")
class WatchLateViewModelFactory(
    private val database: TvShowDao,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchLateViewModel::class.java)) {
            return WatchLateViewModel(database, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}