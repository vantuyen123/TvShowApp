package com.example.material.mosttvshow.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.material.mosttvshow.domain.TvShow

@Entity
data class DatabaseTvShows constructor(

    @PrimaryKey
    val id:Int,

    val country:String,

    val imageThumbnailPath:String,

    val name:String,

    val network:String,

    val permalink:String,

    val startDate:String,

    val status:String
)

fun List<DatabaseTvShows>.asDomainModel():List<TvShow>{
    return map{
        TvShow(
            id = it.id,
            country = it.country,
            imageThumbnailPath = it.imageThumbnailPath,
            name =it.name,
            network = it.network,
            permalink = it.permalink,
            startDate = it.startDate,
            status = it.status
        )
    }
}