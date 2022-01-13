package com.example.movieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.movieapp.data.Film
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.data.repo.FirebaseRepo

class FragmentProfileViewModel : ViewModel() {
    private val repo = FirebaseRepo()

    val user = repo.getUserData()
    /*val favFilms = user.switchMap {
        repo.getFavFilms(it.favFilms)
    }*/
   val favMovies = user.switchMap {
       repo.getFavMovies(it.favFilms)
   }
    fun deleteFavMovie(movie: MovieDetails){
        repo.deleteFavMovies(movie)
    }
    fun deleteWatchedMovie(movie: MovieDetails){
        repo.deleteWatchedMovies(movie)
    }fun deleteToWatchMovie(movie: MovieDetails){
        repo.deleteToWatchMovies(movie)
    }
    val watchedMovies = user.switchMap {
        repo.getWatchedMovies(it.watchedMovie)
    }
    val toWatchMovies = user.switchMap {
        repo.getToWatchMovies(it.toWatchMovie)
    }

    fun editProfileData(map: Map<String, String>){
        repo.editProfileData(map)
    }
}