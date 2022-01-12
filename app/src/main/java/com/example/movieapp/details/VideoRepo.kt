package com.example.movieapp.details

import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.Video
import com.example.movieapp.data.o.VideoResponse
import com.example.movieapp.data.repo.VideoDataSource

class VideoRepo() {
    //private val apiService: MovieDBInterface, private val retrofit: MovieDBClient to bylo w argumentach
    /*fun fetchVideo(compositeDisposable: CompositeDisposable, movieId: Int):LiveData<Video> {
        videoDataSource = VideoDataSource(apiService,compositeDisposable)
        videoDataSource.fetchVideos(movieId)
        return videoDataSource.videoResponse
    }*/
    suspend fun getVideos(movieId: Int): VideoResponse{
        /*List<Video>*/

        val apiService2 = MovieDBClient.getClient()
        return apiService2.getVideo(movieId)
    }
}