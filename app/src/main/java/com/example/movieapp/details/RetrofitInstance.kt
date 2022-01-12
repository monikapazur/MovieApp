package com.example.movieapp.details

import retrofit2.Retrofit

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
    }
}