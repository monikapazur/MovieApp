package com.example.movieapp.data.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.data.o.PopularMovies
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POST_PER_PAGE
import com.example.movieapp.data.repo.MovieDataSource
import com.example.movieapp.data.repo.MovieDataSourceFactory
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepo (private val apiService: MovieDBInterface){
    lateinit var moviePagedList: LiveData<PagedList<PopularMovies>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable
    ):LiveData<PagedList<PopularMovies>>{
        moviesDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory,config).build()

        return moviePagedList
    }
    fun getNetworkState():LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }
}