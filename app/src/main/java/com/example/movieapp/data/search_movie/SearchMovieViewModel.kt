package com.example.movieapp.data.search_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.o.SearchMovie
import com.example.movieapp.data.o.SearchMovieResponse
import com.example.movieapp.data.o.VideoResponse
import com.example.movieapp.details.VideoRepo
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val searchMovieRepo: SearchMovieRepo,query: String) : ViewModel() {
    val searchMovieResponse : MutableLiveData<SearchMovieResponse> = MutableLiveData()

    fun getSearchMovie(query: String){

        viewModelScope.launch {
            val response = searchMovieRepo.getSearchMovies(query)
            searchMovieResponse.value = response
        }
    }
}