package com.example.movieapp.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieapp.data.api.FIRST_PAGE
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.UpcomingMovie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UpcomingMovieDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, UpcomingMovie>() {

    private var page = FIRST_PAGE

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UpcomingMovie>
    ) {
    networkState.postValue(NetworkState.LOADING)

    compositeDisposable.add(
        apiService.getUpcomingMovie(page)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    callback.onResult(it.upcomingMoviesList,null,page+1)
                },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("UpcomingMovieDataSource", it.message.toString())
                }
            )
    )


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UpcomingMovie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UpcomingMovie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getUpcomingMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key){
                            callback.onResult(it.upcomingMoviesList,params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }

                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("UpcomingMovieDataSource", it.message.toString())
                    }
                )
        )
    }
}