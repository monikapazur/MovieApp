package com.example.movieapp.data.upcoming_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapp.data.o.UpcomingMovie
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class UpcomingMovieViewModel(private val upcomingMovieRepo: UpcomingMoviePagedListRepo) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val upcomingMoviePagedList: LiveData<PagedList<UpcomingMovie>> by lazy {
        upcomingMovieRepo.fetchLiveUpcomingMoviePagedList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        upcomingMovieRepo.getNetworkState()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}