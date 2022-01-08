package com.example.movieapp.data.now_playing_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapp.data.o.NowPlayingMovie
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class NowPlayingMovieViewModel(private val nowPlayingMovieRepo : NowPlayingMoviePagedListRepo):ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val nowPlayingMoviePagedList : LiveData<PagedList<NowPlayingMovie>> by lazy {
        nowPlayingMovieRepo.fetchLiveNowPlayingMoviePagedList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        nowPlayingMovieRepo.getNetworkState()
    }
    fun nowPlayingListIsEmpty():Boolean {
        return nowPlayingMoviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}