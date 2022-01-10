package com.example.movieapp.details

import androidx.lifecycle.LiveData
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.Video
import com.example.movieapp.data.repo.MovieDetailsNetDataSource
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepo(private val apiService :MovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetDataSource
    fun fetchSingleDetails(compositeDisposable: CompositeDisposable, movieId:Int):LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }
    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }

    fun getVideo(movie_id: Int): Single<List<Video>> {
        return getVideo(movie_id)
    }
}