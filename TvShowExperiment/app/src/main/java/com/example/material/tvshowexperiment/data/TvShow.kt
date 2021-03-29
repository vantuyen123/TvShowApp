package com.example.material.tvshowexperiment.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Suppress("DEPRECATED_ANNOTATION")
@JsonClass(generateAdapter = true)
@Parcelize
@Entity(tableName = "tvShowDb")
data class TvShow(

    @Json(name = "id")
    @PrimaryKey
    val id: Long,
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
    val startDate: String? = null,
    @Json(name = "status")
    val status: String
) : Parcelable