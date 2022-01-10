package com.example.movieapp.data.o

import com.google.gson.annotations.SerializedName


data class VideoResponse(
    val id: Int,
    @SerializedName("results")
    val videosList: List<Video>
)