package com.example.movieapp.data.top_rated_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapp.data.o.TopRatedMovie
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class TopRatedMovieViewModel(private val topRatedMovieRepo: TopRatedMoviePagedListRepo) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val topRatedMoviePagedList: LiveData<PagedList<TopRatedMovie>> by lazy {
        topRatedMovieRepo.fetchLiveTopRatedMoviePagedList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        topRatedMovieRepo.getNetworkState()
    }
    fun topRatedMovieListIsEmpty() : Boolean {
        return topRatedMoviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}