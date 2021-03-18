package com.example.material.mosttvshow.domain


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShow(

    @Json(name = "id")
    val id: Int,
    @Json(name = "country")
    val country: String,
    @Json(name = "image_thumbnail_path")
    val imageThumbnailPath: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "network")
    val network: String,
    @Json(name = "permalink")
    val permalink: String,
    @Json(name = "start_date")
    val startDate: String,
    @Json(name = "status")
    val status: String
)

