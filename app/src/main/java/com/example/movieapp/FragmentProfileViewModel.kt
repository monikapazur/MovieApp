package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.movieapp.data.Film
import com.example.movieapp.data.User
import com.example.movieapp.data.repo.FirebaseRepo
import com.example.movieapp.details.SingleMovieViewModel

class FragmentProfileViewModel : ViewModel() {
    private val repo = FirebaseRepo()

    val user = repo.getUserData()
    /*val favFilms = user.switchMap {
        repo.getFavFilms(it.favFilms)
    }*/
   val favMovies = user.switchMap {
       repo.getFavMovies(it.favFilms)
   }
    fun deleteFavFilms(film: Film){
        repo.deleteFavFilm(film)
    }

    fun editProfileData(map: Map<String, String>){
        repo.editProfileData(map)
    }
}