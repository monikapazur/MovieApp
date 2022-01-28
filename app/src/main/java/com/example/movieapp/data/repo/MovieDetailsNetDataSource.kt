package com.example.movieapp.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val downloadedMovieDetailsResponse_ = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
        get() = downloadedMovieDetailsResponse_

    fun fetchMovieDetails(movie_id: Int) {
        try {
            compositeDisposable.add(
                apiService.getMovieDet(movie_id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            downloadedMovieDetailsResponse_.postValue(it)
                        },
                        {
                            Log.e("MovieDetailsDataSource", it.message.toString())

                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDetailsDataSource", e.message.toString())
        }

    }
}