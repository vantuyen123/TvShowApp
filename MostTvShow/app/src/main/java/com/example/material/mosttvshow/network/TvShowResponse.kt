package com.example.material.mosttvshow.network


import com.example.material.mosttvshow.database.DatabaseTvShows
import com.example.material.mosttvshow.domain.TvShow
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "tv_shows")
    val tvShows: List<TvShow>
)

fun  TvShowResponse.asDatabaseModel():List<DatabaseTvShows>{
    return tvShows.map {
        DatabaseTvShows(
            id = it.id,
            country = it.country,
            imageThumbnailPath = it.imageThumbnailPath,
            name = it.name,
            network = it.network,
            permalink = it.permalink,
            startDate = it.startDate,
            status = it.status
        )
    }
}