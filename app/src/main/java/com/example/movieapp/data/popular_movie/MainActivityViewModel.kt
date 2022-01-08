package com.example.movieapp.data.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapp.data.o.PopularMovies
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepo: MoviePageListRepo):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<PopularMovies>> by lazy{
        movieRepo.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy{
        movieRepo.getNetworkState()
    }
    fun listIsEmpty(): Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}