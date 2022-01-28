package com.example.movieapp.data.search_movie

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.popular_movie.MainActivityViewModel
import com.example.movieapp.data.popular_movie.MoviePageListRepo
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepo: MoviePageListRepo

    private lateinit var searchMovieRepo: SearchMovieRepo


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieRepo = MoviePageListRepo(apiService)

        viewModel = getViewModel()

        val searchMovieAdapter = SearchMovieListAdapter(requireContext())
        val linearLayoutManagerPopularMovies =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_search_movie_list.layoutManager = linearLayoutManagerPopularMovies
        rv_search_movie_list.setHasFixedSize(true)
        rv_search_movie_list.adapter = searchMovieAdapter


        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                var query = s.toString()
                if (query.isNotEmpty()) {
                    searchMovieRepo = SearchMovieRepo()
                    var searchMovieVm = getSearchMovieViewModel(query)
                    searchMovieVm.getSearchMovie(query)
                    searchMovieVm.searchMovieResponse.observe(viewLifecycleOwner, {

                        searchMovieAdapter.setSearchMovies(it.searchMoviesList)
                    })
                }

            }

        })

    }

    /* private val mIssuePostLiveData = MutableLiveData<MutableList<PopularMovies>>()

     init {
         mIssuePostLiveData.value = ArrayList()
     }

     fun addIssuePost(issuePost: PopularMovies) {
         mIssuePostLiveData.value?.add(issuePost)
         mIssuePostLiveData.value = mIssuePostLiveData.value
     }*/
    fun filterList(filterItem: String) {
        /* lateinit var tempList: PagedList<PopularMovies>

         viewModel.moviePagedList.observe(viewLifecycleOwner,{
             for (i in it){
                 if(filterItem in i.title.toString()){
                     tempList.addAll(it)
                 }
             }
         })*/
    }

    /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
          inflater.inflate(R.menu.search_menu, menu)
          val item = menu.findItem(R.id.search_action)
          val searchView = item?.actionView as SearchView

          searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
              override fun onQueryTextSubmit(query: String?): Boolean {
                  TODO("Not yet implemented")
              }

              override fun onQueryTextChange(newText: String?): Boolean {
                  tempArrayList.clear()
                  val searchText = newText!!
                  if(searchText.isNotEmpty()){
                      newArrayList.forEach {
                          if(it.title.contains(searchText)){
                              tempArrayList.add(it)
                          }
                      }

                      rv_search_movie_list.adapter!!.notifyDataSetChanged()
                  }
                  else{
                      tempArrayList.clear()
                      tempArrayList.addAll(newArrayList)
                      rv_search_movie_list.adapter!!.notifyDataSetChanged()
                  }
                  return false
              }

          })
          return super.onCreateOptionsMenu(menu, inflater)

      }
  */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepo) as T
            }
        })[MainActivityViewModel::class.java]
    }

    private fun getSearchMovieViewModel(query: String): SearchMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchMovieViewModel(searchMovieRepo, query) as T
            }
        })[SearchMovieViewModel::class.java]
    }
}

