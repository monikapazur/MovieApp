package com.example.movieapp.data.o


import com.google.gson.annotations.SerializedName

data class UpcomingMovieResponse(
    val page: Int,
    @SerializedName("results")
    val upcomingMoviesList: List<UpcomingMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)