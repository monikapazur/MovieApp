package com.example.movieapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.example.movieapp.data.api.MovieDBClient
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.PopularMovies
import com.example.movieapp.data.popular_movie.MainActivityViewModel
import com.example.movieapp.data.popular_movie.MoviePageListRepo
import com.example.movieapp.data.popular_movie.PopularMoviePagedListAdapter
import com.example.movieapp.data.popular_movie.PopularMoviePagedListAdapterToSearch
import kotlinx.android.synthetic.main.fragment_movies.*
import java.util.*
import kotlin.collections.ArrayList


class MoviesFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepo: MoviePageListRepo

    private lateinit var tempArrayList: PagedList<PopularMovies>
    private lateinit var newArrayList: Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        movieRepo = MoviePageListRepo(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapterToSearch(requireContext())
        val linearLayoutManagerPopularMovies =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_search_movie_list.layoutManager = linearLayoutManagerPopularMovies
        rv_search_movie_list.setHasFixedSize(true)
        rv_search_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(viewLifecycleOwner, {
            movieAdapter.submitList(it)
           /* newArrayList.addAll(it)
            tempArrayList.addAll(it)
            movieAdapter.submitList(tempArrayList)*/

        })



        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                lateinit var tempList: LiveData<PagedList<PopularMovies>>

                viewModel.moviePagedList.observe(viewLifecycleOwner,{
                    for (i in it){
                        if(i.title.contains(searchEditText.text)){
                        tempList.value?.add(i)
                        }
                    }

                })
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
    fun filterList(filterItem: String){
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
}

