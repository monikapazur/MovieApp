package com.example.movieapp.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieapp.data.o.NowPlayingMovie
import com.example.movieapp.data.api.FIRST_PAGE
import com.example.movieapp.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NowPlayingMovieDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, NowPlayingMovie>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NowPlayingMovie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getNowPlayingMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.nowPlayingMoviesList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieSource Now Plaing", it.message.toString())
                    }
                )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NowPlayingMovie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NowPlayingMovie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getNowPlayingMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                       if(it.totalPages >= params.key){
                           callback.onResult(it.nowPlayingMoviesList, params.key + 1)
                       }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                       }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieSource Now Plaing", it.message.toString())
                    }
                )
        )
    }
}