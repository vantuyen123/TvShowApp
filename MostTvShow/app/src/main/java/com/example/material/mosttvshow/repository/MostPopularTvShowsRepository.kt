package com.example.material.mosttvshow.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.material.mosttvshow.database.TvShowsDatabase
import com.example.material.mosttvshow.database.asDomainModel
import com.example.material.mosttvshow.domain.TvShow
import com.example.material.mosttvshow.network.TvShowApi
import com.example.material.mosttvshow.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MostPopularTvShowsRepository(private val database: TvShowsDatabase) {
    suspend fun refresherTvShows(page: Int) {
        withContext(Dispatchers.IO) {
            val listTvShows = TvShowApi.retrofitService.getMostPopular(page)
            database.tvShowDao.insertAll(listTvShows.asDatabaseModel())
        }
    }

    val tvShows: LiveData<List<TvShow>> = Transformations.map(database.tvShowDao.getTvShows()) {
        it.asDomainModel()
    }
}