package com.example.movieapp.data

data class User(
    val uid: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val favFilms: List<Int?>? = null,
    val image: String? = null
) {
}