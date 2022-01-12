package com.example.movieapp.data.o


import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    val page: Int,
    @SerializedName("results")
    val searchMoviesList: List<SearchMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)