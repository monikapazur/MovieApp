package com.example.movieapp.data.now_playing_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.data.o.NowPlayingMovie
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POST_PER_PAGE
import com.example.movieapp.data.repo.NetworkState
import com.example.movieapp.data.repo.NowPlayingMovieDataSource
import com.example.movieapp.data.repo.NowPlayingMovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class NowPlayingMoviePagedListRepo(private val apiService: MovieDBInterface) {

    lateinit var nowPlayingMoviePagedList: LiveData<PagedList<NowPlayingMovie>>
    lateinit var nowPlayingMovieDataSourceFactory: NowPlayingMovieDataSourceFactory

    fun fetchLiveNowPlayingMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<NowPlayingMovie>> {
        nowPlayingMovieDataSourceFactory =
            NowPlayingMovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        nowPlayingMoviePagedList = LivePagedListBuilder(nowPlayingMovieDataSourceFactory, config).build()

        return nowPlayingMoviePagedList
    }
    fun getNetworkState():LiveData<NetworkState> {
        return Transformations.switchMap<NowPlayingMovieDataSource, NetworkState>(
            nowPlayingMovieDataSourceFactory.nowPlayingMovieLiveDataSource, NowPlayingMovieDataSource::networkState
        )
    }
}