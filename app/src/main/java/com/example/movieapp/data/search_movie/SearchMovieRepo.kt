package com.example.movieapp.data.search_movie

import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.o.SearchMovie
import com.example.movieapp.data.o.SearchMovieResponse
import com.example.movieapp.data.o.VideoResponse

class SearchMovieRepo {
    suspend fun getSearchMovies(query: String): SearchMovieResponse {

        val apiService2 = MovieDBClient.getClient()
        return apiService2.getSearchMovie(query)
    }
}