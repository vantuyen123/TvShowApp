package com.example.material.tvshowexperiment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.material.tvshowexperiment.data.TvShow

@Database(entities = [TvShow::class], version = 1, exportSchema = false)
abstract class TvShowDatabase : RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var INSTANCE: TvShowDatabase? = null

        fun getInstance(context: Context): TvShowDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TvShowDatabase::class.java,
            "TvShow_db"
        ).build()
    }

}