package com.example.movieapp

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.Film
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.now_playing_movie.NowPlayingMoviePagedListAdapter
import com.example.movieapp.data.now_playing_movie.NowPlayingMoviePagedListRepo
import com.example.movieapp.data.now_playing_movie.NowPlayingMovieViewModel
import com.example.movieapp.data.popular_movie.MainActivityViewModel
import com.example.movieapp.data.popular_movie.MoviePageListRepo
import com.example.movieapp.data.popular_movie.PopularMoviePagedListAdapter
import kotlinx.android.synthetic.main.fragment_home_fragment.*

class FragmentHome : Fragment(), OnFilmItemLongClick {

    private val homeVm by viewModels<FragmentHomeViewModel>()
    private val adapter = FilmAdapter(this)
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var nowPlayingMovieVm : NowPlayingMovieViewModel
    lateinit var nowPlayingMovieRepo : NowPlayingMoviePagedListRepo
    lateinit var movieRepo: MoviePageListRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* rv_movie_list_temp.layoutManager = LinearLayoutManager(requireContext())
        rv_movie_list_temp.setHasFixedSize(true)
        rv_movie_list_temp.adapter = adapter*/

        /*val intent = Intent (getActivity(), PopularMoviesActivity::class.java)
        getActivity()?.startActivity(intent)*/

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieRepo = MoviePageListRepo(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(requireContext())
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        val linearLayoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)


        rv_popular_movie_list.layoutManager = linearLayoutManager
        rv_popular_movie_list.setHasFixedSize(true)
        rv_popular_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(viewLifecycleOwner,  {
            movieAdapter.submitList(it)
        })

        nowPlayingMovieRepo = NowPlayingMoviePagedListRepo(apiService)
        nowPlayingMovieVm = getNowPlayingMovieViewModel()
        val nowPlayingMovieAdapter = NowPlayingMoviePagedListAdapter(requireContext())

        rv_now_movie_list.layoutManager = linearLayoutManager2
        rv_now_movie_list.setHasFixedSize(true)
        rv_now_movie_list.adapter = nowPlayingMovieAdapter

        nowPlayingMovieVm.nowPlayingMoviePagedList.observe(viewLifecycleOwner, {
            nowPlayingMovieAdapter.submitList(it)
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeVm.films.observe(viewLifecycleOwner, { list ->
            adapter.setFilms(list)

        })

    }

    override fun onFilmLongClick(film: Film, position: Int) {
        Toast.makeText(requireContext(), film.name, Toast.LENGTH_LONG).show()
        homeVm.addFavFilm(film)
    }

    private fun getViewModel():MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepo) as T
            }
        })[MainActivityViewModel::class.java]
    }
    private fun getNowPlayingMovieViewModel():NowPlayingMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NowPlayingMovieViewModel(nowPlayingMovieRepo) as T
            }
        })[NowPlayingMovieViewModel::class.java]
    }
}


