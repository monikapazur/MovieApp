package com.example.movieapp.data.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.TopRatedMovie
import io.reactivex.disposables.CompositeDisposable

class TopRatedMovieDataSourceFactory(private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int,TopRatedMovie>() {

    val topRatedMoviesLiveDataSource = MutableLiveData<TopRatedMovieDataSource>()

    override fun create(): DataSource<Int, TopRatedMovie> {
        val topRatedMovieDataSource = TopRatedMovieDataSource(apiService,compositeDisposable)

        topRatedMoviesLiveDataSource.postValue(topRatedMovieDataSource)
        return topRatedMovieDataSource
    }
}