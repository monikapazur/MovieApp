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

class FragmentProfile : Fragment(), OnFilmItemLongClick {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo
    private val profileVm by viewModels<FragmentProfileViewModel>()

    /*private val adapter = FilmAdapter(this)*/
    private val adapter = MovieAdapter()
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
        fav_films_recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fav_films_recyclerView.adapter = adapter


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profileVm.user.observe(viewLifecycleOwner, { user ->
            var list = listOf<Int>()
            val movieFavList: MutableList<MovieDetails> = mutableListOf()
            val apiService: MovieDBInterface = MovieDBClient.getClient()
            movieRepository = MovieDetailsRepo(apiService)
            user.favFilms.let {
                list = it as List<Int>
            }
            /* for (i in list){
                 viewModel = getViewModel(i)
             }*/
            for (i in list) {
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
                /*  Toast.makeText(requireContext(),"okej1",Toast.LENGTH_SHORT).show()
                  viewModel.movieDetails.observe(viewLifecycleOwner, {

                      movieFavList.add(it)
                      Toast.makeText(requireContext(),"okej2",Toast.LENGTH_SHORT).show()
  */
                //})
            }
/*
                adapter . setFavMovies (movieFavList)*/
            bindUserData(user)
        })


    }

    override fun onFilmLongClick(film: Film, position: Int) {
        /*profileVm.deleteFavFilms(film)
        adapter.deleteFilm(film, position)*/
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
}