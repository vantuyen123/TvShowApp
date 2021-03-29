package com.example.material.tvshowexperiment.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.material.tvshowexperiment.data.TvShow
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class TvShowDatabaseTest {
    private lateinit var tvShowDao: TvShowDao
    private lateinit var db: TvShowDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, TvShowDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        tvShowDao = db.tvShowDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTvShow() {
        val tvShow = TvShow(1,1.toString(),1.toString(),1.toString(),1.toString(),1.toString(),1.toString(),1.toString())
        tvShowDao.insertMovie(tvShow)
        val getMovie = tvShowDao.getTvShowFromWatchList(1)
        tvShowDao.getWatchList()

    }
}