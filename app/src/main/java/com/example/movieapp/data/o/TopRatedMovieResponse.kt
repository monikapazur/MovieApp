package com.example.movieapp.data.o


import com.google.gson.annotations.SerializedName

data class TopRatedMovieResponse(
    val page: Int,
    @SerializedName("results")
    val topRatedMoviesList: List<TopRatedMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)