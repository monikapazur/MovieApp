package com.example.movieapp.details

import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.o.VideoResponse

class VideoRepo() {

    suspend fun getVideos(movieId: Int): VideoResponse{
        val apiService2 = MovieDBClient.getClient()
        return apiService2.getVideo(movieId)
    }
}