package com.example.tvshowsapp.network;

import com.example.tvshowsapp.response.TVShowDetailsResponse;
import com.example.tvshowsapp.response.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponse> getTvShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShowResponse> searchTVShow(@Query("q") String query, @Query("page") int page);
}
