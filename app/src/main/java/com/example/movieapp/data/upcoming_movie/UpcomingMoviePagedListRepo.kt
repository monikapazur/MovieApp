package com.example.movieapp.data.upcoming_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POST_PER_PAGE
import com.example.movieapp.data.o.UpcomingMovie
import com.example.movieapp.data.repo.NetworkState
import com.example.movieapp.data.repo.UpcomingMovieDataSource
import com.example.movieapp.data.repo.UpcomingMovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class UpcomingMoviePagedListRepo(private val apiService: MovieDBInterface) {

   lateinit var upcomingMoviePagedList: LiveData<PagedList<UpcomingMovie>>

    lateinit var upcomingMovieDataSourceFactory: UpcomingMovieDataSourceFactory

    fun fetchLiveUpcomingMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<UpcomingMovie>> {
        upcomingMovieDataSourceFactory =
            UpcomingMovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        upcomingMoviePagedList =
            LivePagedListBuilder(upcomingMovieDataSourceFactory, config).build()

        return upcomingMoviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<UpcomingMovieDataSource, NetworkState>(
            upcomingMovieDataSourceFactory.upcomingMoviesLiveDataSource,
            UpcomingMovieDataSource::networkState
        )


    }
}