package com.example.movieapp

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Film
import com.example.movieapp.data.repo.FirebaseRepo

class FragmentHomeViewModel : ViewModel() {
    private val repo = FirebaseRepo()

    val films = repo.getMovies()

    fun addFavFilm(film: Film){
        repo.addFavFilm(film)
    }
}