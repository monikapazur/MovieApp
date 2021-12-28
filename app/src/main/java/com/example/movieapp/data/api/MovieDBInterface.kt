package com.example.movieapp.data.api

import com.example.movieapp.data.`object`.MovieDetails
import com.example.movieapp.data.`object`.MovieDetailsVideo
import com.example.movieapp.data.`object`.MovieResponse
import com.example.movieapp.data.`object`.MovieVideo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
https://api.themoviedb.org/3/movie/popular?api_key=e7294c2a2fdb6cf9277febb3aad59c96&language=en-US&page=1

https://api.themoviedb.org/3/movie/550?api_key=e7294c2a2fdb6cf9277febb3aad59c96

https://api.themoviedb.org/3/movie/343611?api_key=e7294c2a2fdb6cf9277febb3aad59c96

zwiastuny:
https://api.themoviedb.org/3/movie/%7Bmovie_id%7D/videos?api_key=e7294c2a2fdb6cf9277febb3aad59c96&language=en-US

baza:
https://api.themoviedb.org/3/

*/

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse> // do tego zeby wzial numer stony z linku page=1

    @GET("movie/{movie_id}")
    fun getMovieDet(@Path("movie_id") id:Int): Single<MovieDetails>

    @GET("movie/{movie_id}/videos")
    fun getMovieDetailsVideo(@Path("movie_id") id:Int): Single<MovieDetailsVideo>

}