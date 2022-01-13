package com.example.movieapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.Film
import com.example.movieapp.data.User
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.details.MovieDetailsRepo
import com.example.movieapp.details.SingleMovieViewModel
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_profile_fragment.*

class FragmentProfile : Fragment(), OnMovieItemLongClick, OnWatchedMovieItemLongClick, OnToWatchMovieItemLongClick {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo
    private val profileVm by viewModels<FragmentProfileViewModel>()
    lateinit var adapter: FavMovieAdapter
    /*private val adapter = FilmAdapter(this)*/

    lateinit var watchedAdapter: WatchedMovieAdapter
    lateinit var toWatchAdapter: ToWatchMovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpProfileData()
        profileLogout()
        adapter = FavMovieAdapter(requireContext(),this)
        fav_films_recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fav_films_recyclerView.adapter = adapter

        watchedAdapter = WatchedMovieAdapter(requireContext(),this)
        var watchedMoviesLinearLayout =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        watched_movies_recyclerView.layoutManager = watchedMoviesLinearLayout
        watched_movies_recyclerView.adapter = watchedAdapter

        toWatchAdapter = ToWatchMovieAdapter(requireContext(),this)
        var toWatchMoviesLinearLayout =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        toWatchMovies_recyclerView.layoutManager = toWatchMoviesLinearLayout
        toWatchMovies_recyclerView.adapter = toWatchAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profileVm.user.observe(viewLifecycleOwner, { user ->
            var favList = listOf<Int>()
            var toWatchList = listOf<Int>()
            var watchedList = listOf<Int>()
            val movieFavList: MutableList<MovieDetails> = mutableListOf()
            val toWatchMovieList: MutableList<MovieDetails> = mutableListOf()
            val watchedMovieList: MutableList<MovieDetails> = mutableListOf()
            val apiService: MovieDBInterface = MovieDBClient.getClient()
            movieRepository = MovieDetailsRepo(apiService)
            user.favFilms.let {
                favList = it as List<Int>
            }
            user.watchedMovie.let {
                watchedList = it as List<Int>
            }
            user.toWatchMovie.let {
                toWatchList = it as List<Int>
            }
            for (i in favList) {
                viewModel = getViewModel(i)
                val fetchSingleDetails =
                    movieRepository.fetchSingleDetails(CompositeDisposable(), i)
                        .observe(viewLifecycleOwner,
                            {
                                Toast.makeText(
                                    requireContext(),
                                    it.originalTitle,
                                    Toast.LENGTH_SHORT
                                ).show()
                                movieFavList.add(it)
                                adapter.setFavMovies(movieFavList)
                            })

            }
            for (i in watchedList) {
                viewModel = getViewModel(i)
                val fetchSingleDetails =
                    movieRepository.fetchSingleDetails(CompositeDisposable(), i)
                        .observe(viewLifecycleOwner,
                            {
                                Toast.makeText(
                                    requireContext(),
                                    it.originalTitle,
                                    Toast.LENGTH_SHORT
                                ).show()
                                watchedMovieList.add(it)
                                watchedAdapter.setWatchedMovies(watchedMovieList)
                            })

            }
            for (i in toWatchList) {
                viewModel = getViewModel(i)
                val fetchSingleDetails =
                    movieRepository.fetchSingleDetails(CompositeDisposable(), i)
                        .observe(viewLifecycleOwner,
                            {
                                Toast.makeText(
                                    requireContext(),
                                    it.originalTitle,
                                    Toast.LENGTH_SHORT
                                ).show()
                                toWatchMovieList.add(it)
                                toWatchAdapter.setToWatchMovies(toWatchMovieList)
                            })

            }

            bindUserData(user)
        })


    }

    private fun bindUserData(user: User) {
        yourNameEditText.setText(user.name)
        yourSurnameEditText.setText(user.surname)
        yourEmailEditText.setText(user.email)
        Log.d("PROFILE_DEBUG", user.toString())
    }

    private fun setUpProfileData() {
        submitButton.setOnClickListener {
            val name = yourNameEditText.text.trim().toString()
            val surname = yourSurnameEditText.text.trim().toString()

            val map = mapOf("name" to name, "surname" to surname)
            profileVm.editProfileData(map)
        }
    }

    private fun profileLogout() {
        logoutBtn.setOnClickListener {
            auth.signOut()
            requireActivity().finish()
        }
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }

    override fun onMovieLongClick(movie: MovieDetails, position: Int) {
        profileVm.deleteFavMovie(movie)
        adapter.deleteMovie(movie, position)

    }

    override fun onWatchedMovieLongClick(movie: MovieDetails, position: Int) {
        profileVm.deleteWatchedMovie(movie)
        watchedAdapter.deleteWatchedMovie(movie,position)
    }

    override fun onToWatchMovieLongClick(movie: MovieDetails, position: Int) {
        profileVm.deleteToWatchMovie(movie)
        toWatchAdapter.deleteToWatchMovie(movie,position)

    }
}