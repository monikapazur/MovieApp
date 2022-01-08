package com.example.movieapp.data.popular_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.google.android.material.bottomnavigation.BottomNavigationView

class PopularMoviesActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepo: MoviePageListRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)
/*
        supportActionBar?.hide()

        val apiService: MovieDBInterface = MovieDBClient.getClient()

        movieRepo = MoviePageListRepo(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)


        var rv_movie_list = findViewById<RecyclerView>(R.id.rv_movie_list)
        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })*/

       /* viewModel.networkState.observe(this, Observer {
            nananan
        })*/

        /*val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController = findNavController(R.id.navigationHostFragment)
        bottomNavigationView.setupWithNavController(navController)*/



    }
    private fun getViewModel():MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepo) as T
            }
        })[MainActivityViewModel::class.java]
    }
/*    override fun onSupportNavigateUp(): Boolean {

       val navController = findNavController(R.id.navigationHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
        */

}