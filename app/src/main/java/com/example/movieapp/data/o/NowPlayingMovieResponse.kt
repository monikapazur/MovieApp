package com.example.movieapp.data.o



import com.example.movieapp.data.o.Dates
import com.google.gson.annotations.SerializedName

data class NowPlayingMovieResponse(
    val dates: Dates,
    val page: Int,
    @SerializedName("results")
    val nowPlayingMoviesList: List<NowPlayingMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)