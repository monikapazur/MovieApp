package com.example.movieapp.details

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POSTER_BASE_URL
import kotlinx.android.synthetic.main.activity_single_details.*


class SingleDetails : AppCompatActivity()  {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo
    var id_fav_movie:Int? = null
    var favList: MutableList<MovieDetails>? = null

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

            /*    viewModel.getVideo(it.id)*/

        })

        /*viewModel.videos.observe(this, Observer {
            it.
        })*/

        /*viewModel.networkState.observe() tutaj wrocic ładowanie progress bar errory jakby co*/

        add_to_favMovies.setOnClickListener {
            viewModel.movieDetails.observe(this, Observer {
                viewModel.addFavMovie(it)
            })
        }
    }


    private fun bindUI(it: MovieDetails) {

        findViewById<TextView>(R.id.movie_title).text = it.title
        findViewById<TextView>(R.id.movie_release_date).text = it.releaseDate
        findViewById<TextView>(R.id.descriptionTextView).text = it.overview
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