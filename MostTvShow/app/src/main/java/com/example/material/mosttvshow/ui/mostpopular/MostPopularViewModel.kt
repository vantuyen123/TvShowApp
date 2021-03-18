package com.example.material.mosttvshow.ui.mostpopular

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.material.mosttvshow.database.getDatabase
import com.example.material.mosttvshow.repository.MostPopularTvShowsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MostPopularViewModel(application: Application) : AndroidViewModel(application) {

    private val mostPopularTvShowsRepository =
        MostPopularTvShowsRepository(getDatabase(application))
    val tvShows = mostPopularTvShowsRepository.tvShows


    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository(1)
    }


    private fun refreshDataFromRepository(page: Int) {
        viewModelScope.launch {
            try {
                mostPopularTvShowsRepository.refresherTvShows(page)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                Log.d("Tag", "${mostPopularTvShowsRepository.refresherTvShows(page)}")
            } catch (networkError: IOException) {
                if (tvShows.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                }
            }
        }
    }


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

}