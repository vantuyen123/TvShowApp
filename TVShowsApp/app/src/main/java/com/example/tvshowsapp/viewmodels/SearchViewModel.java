package com.example.tvshowsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowsapp.repository.SearchTVShowRepository;
import com.example.tvshowsapp.response.TVShowResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowResponse> searchTVShow(String query,int page){
        return searchTVShowRepository.searchTVShow(query,page);
    }
}
