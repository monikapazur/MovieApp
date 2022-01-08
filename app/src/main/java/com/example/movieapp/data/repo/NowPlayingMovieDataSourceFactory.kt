package com.example.movieapp.data.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieapp.data.o.NowPlayingMovie
import com.example.movieapp.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable


class NowPlayingMovieDataSourceFactory(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, NowPlayingMovie>(){

    val nowPlayingMovieLiveDataSource = MutableLiveData<NowPlayingMovieDataSource>()

    override fun create(): DataSource<Int, NowPlayingMovie> {
       val nowPlayingMovieDataSource = NowPlayingMovieDataSource(apiService,compositeDisposable)


        nowPlayingMovieLiveDataSource.postValue(nowPlayingMovieDataSource)

        return nowPlayingMovieDataSource
    }
}