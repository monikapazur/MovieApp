package com.example.movieapp.data.repo

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieapp.data.`object`.PopularMovies
import com.example.movieapp.data.api.FIRST_PAGE
import com.example.movieapp.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
):PageKeyedDataSource<Int, PopularMovies>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PopularMovies>
    ) {
        networkState.postValue(NetworkState.LOADED)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.popularMoviesList, null,page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString())

                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovies>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovies>) {
        networkState.postValue(NetworkState.LOADED)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key) // automatycznie zmieniajaca sie strona
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){ // wiecej stron do zaladowania
                            callback.onResult(it.popularMoviesList,params.key+1)
                            networkState.postValue(NetworkState.LOADED)

                        }
                        else{//kiedy nie mamy juz stron
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString())

                    }
                )
        )
    }
}