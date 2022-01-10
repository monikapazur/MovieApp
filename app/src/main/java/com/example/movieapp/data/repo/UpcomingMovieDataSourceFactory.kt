package com.example.movieapp.data.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.UpcomingMovie
import io.reactivex.disposables.CompositeDisposable

class UpcomingMovieDataSourceFactory(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, UpcomingMovie>() {

    val upcomingMoviesLiveDataSource = MutableLiveData<UpcomingMovieDataSource>()

    override fun create(): DataSource<Int, UpcomingMovie> {
        val upcomingMovieDataSource = UpcomingMovieDataSource(apiService, compositeDisposable)

        upcomingMoviesLiveDataSource.postValue(upcomingMovieDataSource)
        return upcomingMovieDataSource
    }
}