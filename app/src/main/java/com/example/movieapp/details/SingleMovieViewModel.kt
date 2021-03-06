package com.example.movieapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.data.o.Video
import com.example.movieapp.data.repo.FirebaseRepo
import com.example.movieapp.data.repo.NetworkState
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepo, movieId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val repo = FirebaseRepo()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleDetails(compositeDisposable, movieId)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun addFavMovie(movie: MovieDetails) {
        repo.addFavMovie(movie)
    }
    fun addToWatchMovie(movie: MovieDetails){
        repo.addToWatchMovie(movie)
    }
    fun addToWatchedMovie(movie: MovieDetails){
        repo.addToWatchedMovie(movie)
    }

}