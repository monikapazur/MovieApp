package com.example.movieapp.details

import androidx.lifecycle.LiveData
import com.example.movieapp.data.`object`.MovieDetails
import com.example.movieapp.data.`object`.MovieDetailsVideo
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.repo.MovieDetailsNetDataSource
import com.example.movieapp.data.repo.MovieDetailsVideoNetDataSource
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsVideoRepo(private val apiService : MovieDBInterface) {
    lateinit var movieDetailsVideoNetworkDataSource: MovieDetailsVideoNetDataSource
    fun fetchSingleDetailsVideo(compositeDisposable: CompositeDisposable, movieId:Int): LiveData<MovieDetailsVideo> {
        movieDetailsVideoNetworkDataSource = MovieDetailsVideoNetDataSource(apiService,compositeDisposable)
        movieDetailsVideoNetworkDataSource.fetchMovieDetailsVideo(movieId)

        return movieDetailsVideoNetworkDataSource.downloadedMovieVideoResponse
    }
    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsVideoNetworkDataSource.networkState
    }
}