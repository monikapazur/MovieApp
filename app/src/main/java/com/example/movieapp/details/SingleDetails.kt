package com.example.movieapp.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.example.movieapp.data.o.MovieDetails
import kotlinx.android.synthetic.main.activity_single_details.*


class SingleDetails : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo


    private lateinit var videoRepo: VideoRepo


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
        videoRepo = VideoRepo()
        var videoVm = getVideoViewModel(movieId)
        videoVm.getVideo(movieId)
        videoVm.videoResponse.observe(this, {
            /*val key = it[0].key*/
            val key = it.videosList[0].key
            click_to_watch_trailer.setOnClickListener {
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key))
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key)
                )
                try {
                    this.startActivity(appIntent)
                } catch (ex: ActivityNotFoundException) {
                    this.startActivity(webIntent)
                }
            }
        })

        add_to_favMovies.setOnClickListener {
            viewModel.movieDetails.observe(this, Observer {
                viewModel.addFavMovie(it)
                Toast.makeText(
                    this,
                    it.originalTitle + "add to favourite movies",
                    Toast.LENGTH_SHORT
                ).show()
                add_to_favMovies.setImageResource(R.drawable.ic_baseline_star_24)
            })
        }
    }


    private fun bindUI(it: MovieDetails) {

        var list = listOf<String>()
        for (i in it.genres) {
            list = list.plus(i.name.toString())
        }

        findViewById<TextView>(R.id.movie_title).text = it.title
        findViewById<TextView>(R.id.movie_rating).text = it.voteAverage.toString()
        findViewById<TextView>(R.id.movie_release_date).text = it.releaseDate
        findViewById<TextView>(R.id.descriptionTextView).text = it.overview
        findViewById<TextView>(R.id.category).text = list.toString()


        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(findViewById(R.id.iv_movie_poster))

    }


    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }

    private fun getVideoViewModel(movieId: Int): VideoViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return VideoViewModel(videoRepo, movieId) as T
            }
        })[VideoViewModel::class.java]
    }
}