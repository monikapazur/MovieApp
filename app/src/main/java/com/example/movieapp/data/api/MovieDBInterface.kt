package com.example.movieapp.data.api

import com.example.movieapp.data.o.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
popular movies
https://api.themoviedb.org/3/movie/popular?api_key=e7294c2a2fdb6cf9277febb3aad59c96&language=en-US&page=1

now playing movies
https://api.themoviedb.org/3/movie/now_playing?api_key=e7294c2a2fdb6cf9277febb3aad59c96&language=en-US&page=1

top rated movies
https://api.themoviedb.org/3/movie/top_rated?api_key=e7294c2a2fdb6cf9277febb3aad59c96&language=en-US&page=1




https://api.themoviedb.org/3/movie/550?api_key=e7294c2a2fdb6cf9277febb3aad59c96

https://api.themoviedb.org/3/movie/343611?api_key=e7294c2a2fdb6cf9277febb3aad59c96

zwiastuny:
https://api.themoviedb.org/3/movie/%7Bmovie_id%7D/videos?api_key=e7294c2a2fdb6cf9277febb3aad59c96&language=en-US

baza:
https://api.themoviedb.org/3/

*/

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse> // do tego zeby wziac numer stony z linku page=1

    @GET("movie/upcoming")
    fun getUpcomingMovie(@Query("page") page: Int): Single<UpcomingMovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie(@Query("page") page: Int): Single<NowPlayingMovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(@Query("page") page: Int): Single<TopRatedMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDet(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideo(
        @Path("movie_id") movieId: Int
    ): VideoResponse
/*
    @GET("group/{id}/users")
    fun groupList(@Path("id") groupId: Int): Call<List<User?>?>?*/
}