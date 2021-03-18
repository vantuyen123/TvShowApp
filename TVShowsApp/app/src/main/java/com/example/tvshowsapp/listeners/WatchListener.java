package com.example.tvshowsapp.listeners;

import com.example.tvshowsapp.models.TVShow;

public interface WatchListener {

    void onTVShowClicked(TVShow tvShow);


    void removeTVShowFromWatchlist(TVShow tvShow, int position);
}
