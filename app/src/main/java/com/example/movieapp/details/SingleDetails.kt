package com.example.movieapp.details

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.`object`.MovieDetails
import com.example.movieapp.data.`object`.MovieDetailsVideo
import com.example.movieapp.data.api.BASE_YT_URL
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class SingleDetails : AppCompatActivity()  {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo
    private lateinit var movieVideo: MovieDetailsVideo
    lateinit var youTubePlayer: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_details)

        supportActionBar?.hide()

        val movieId = intent.getIntExtra("id", 1)
        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailsRepo(apiService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })
        /*viewModel.networkState.observe() tutaj wrocic ładowanie progress bar errory jakby co*/


    }


    private fun bindUI(it: MovieDetails) {

        findViewById<TextView>(R.id.movie_title).text = it.title

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(findViewById(R.id.iv_movie_poster))
        /*youTubePlayer = findViewById(R.id.ytPlayer)*/


        /*val movieVideoURL = BASE_YT_URL + viewModel.movieVideo.value?.key*/

      /*  val movieVideoURL = "www.youtube.com/watch?v=Jd3nTm-wvyA"*/

      /*  VideosFragment()*/

    }


    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }

}