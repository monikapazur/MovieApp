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

    var videos: MutableLiveData<Single<List<Video>>> = MutableLiveData()

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

    fun getVideo(movie_id: Int) {
        viewModelScope.launch {
            val response = movieRepository.getVideo(movie_id)
            videos.value = response
        }
    }
}