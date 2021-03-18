package com.example.tvshowsapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "tvShows")
public class TVShow implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("image_thumbnail_path")
    private String thumbnail;

    @SerializedName("name")
    private String name;

    @SerializedName("network")
    private String network;

    @SerializedName("permalink")
    private String permalink;

    @SerializedName("start_date")
    private String start_date;

    @SerializedName("status")
    private String status;

    @SerializedName("country")
    private String country;

    public int getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getNetwork() {
        return network;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}