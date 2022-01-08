package com.example.movieapp

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.User
import com.example.movieapp.data.repo.FirebaseRepo

class RegistrationViewModel : ViewModel() {

    private val repo = FirebaseRepo()

    fun createNewUser(user: User){
        repo.createNewUser(user)
    }
}