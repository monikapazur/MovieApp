package com.example.movieapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.data.repo.FirebaseRepo
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepo, movieId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val repo = FirebaseRepo()
    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleDetails(compositeDisposable, movieId)
    }
    val movieState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun addFavMovie(movie: MovieDetails) {
        repo.addFavMovie(movie)
    }
}