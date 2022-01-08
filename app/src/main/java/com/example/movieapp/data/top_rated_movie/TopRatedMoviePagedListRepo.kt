package com.example.movieapp.data.top_rated_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POST_PER_PAGE
import com.example.movieapp.data.o.TopRatedMovie
import com.example.movieapp.data.repo.NetworkState
import com.example.movieapp.data.repo.TopRatedMovieDataSource
import com.example.movieapp.data.repo.TopRatedMovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class TopRatedMoviePagedListRepo(private val apiService: MovieDBInterface) {

    lateinit var topRatedMoviePagedList: LiveData<PagedList<TopRatedMovie>>
    lateinit var topRatedMovieDataSourceFactory: TopRatedMovieDataSourceFactory

    fun fetchLiveTopRatedMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<TopRatedMovie>> {
        topRatedMovieDataSourceFactory =
            TopRatedMovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        topRatedMoviePagedList =
            LivePagedListBuilder(topRatedMovieDataSourceFactory, config).build()

        return topRatedMoviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<TopRatedMovieDataSource, NetworkState>(
            topRatedMovieDataSourceFactory.topRatedMoviesLiveDataSource,
            TopRatedMovieDataSource::networkState
        )
    }
}