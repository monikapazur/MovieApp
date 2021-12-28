package com.example.movieapp.details

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.`object`.MovieDetails
import com.example.movieapp.data.`object`.MovieDetailsVideo
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepo,movieId:Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy{
        movieRepository.fetchSingleDetails(compositeDisposable,movieId)
    }
    val movieState : LiveData<NetworkState> by lazy{
        movieRepository.getMovieDetailsNetworkState()
    }
    val movieVideo : LiveData<MovieDetailsVideo> by lazy{
        movieRepository.getMovieDetailsVideo()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}