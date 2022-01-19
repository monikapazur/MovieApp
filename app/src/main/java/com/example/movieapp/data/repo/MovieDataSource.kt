package com.example.movieapp.data.repo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieapp.data.o.PopularMovies
import com.example.movieapp.data.api.FIRST_PAGE
import com.example.movieapp.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.coroutineContext

class MovieDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
):PageKeyedDataSource<Int, PopularMovies>() {
    private var page = FIRST_PAGE
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PopularMovies>
    ) {
        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.popularMoviesList, null,page+1)
                    },
                    {
                        Log.e("MovieDataSource", it.message.toString())
                    }
                )
        )
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovies>) {
        compositeDisposable.add(
            apiService.getPopularMovie(params.key) // automatycznie zmieniajaca sie strona
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){ // wiecej stron do zaladowania
                            callback.onResult(it.popularMoviesList,params.key+1)
                        }
                        else{
                           Log.d("END OF LIST", it.toString())
                        }
                    },
                    {
                        Log.e("MovieDataSourceError", it.message.toString())

                    }
                )
        )
    }
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovies>) {
    }
}