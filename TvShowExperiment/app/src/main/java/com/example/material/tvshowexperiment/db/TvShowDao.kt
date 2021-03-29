package com.example.material.tvshowexperiment.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.material.tvshowexperiment.data.TvShow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(tvShow: TvShow)

    @Query("SELECT * FROM tvShowDb")
    fun getWatchList(): LiveData<List<TvShow>>

    @Query("SELECT * From tvShowDb Where id =:id")
    suspend fun getTvShowFromWatchList(id: Int): TvShow

    @Query("SELECT Exists(SELECT *FROM tvShowDb Where id=:id)")
    fun isRowIsExists(id:Int): Boolean?

}