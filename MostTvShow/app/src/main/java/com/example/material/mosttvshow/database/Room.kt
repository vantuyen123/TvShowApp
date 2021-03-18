package com.example.material.mosttvshow.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TvShowDao {
    @Query("select * from databasetvshows")
    fun getTvShows(): LiveData<List<DatabaseTvShows>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tvShows: List<DatabaseTvShows>)
}

@Database(entities = [DatabaseTvShows::class], version = 1, exportSchema = false)
abstract class TvShowsDatabase : RoomDatabase() {
    abstract val tvShowDao: TvShowDao
}

private lateinit var INSTANCE: TvShowsDatabase

fun getDatabase(context: Context): TvShowsDatabase {
    synchronized(TvShowsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TvShowsDatabase::class.java,
                "tv_shows_db"
            )
                .build()
        }
    }
    return INSTANCE
}