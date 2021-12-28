package com.example.movieapp.data.repo

import android.bluetooth.BluetoothAdapter.ERROR
import android.util.Log
import android.util.Log.ERROR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.`object`.MovieDetails
import com.example.movieapp.data.`object`.MovieDetailsVideo
import com.example.movieapp.data.api.MovieDBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val networkState_ = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = networkState_ //dzieki temu mozemy dostac sie do LiveData ktory z gory jest nie mutowalny

    private val movieVideo_ = MutableLiveData<MovieDetailsVideo>()
    val movieVideo: LiveData<MovieDetailsVideo>
            get() = movieVideo_

    private val downloadedMovieDetailsResponse_ = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
    get() = downloadedMovieDetailsResponse_

    fun fetchMovieDetails(movie_id: Int){
        networkState_.postValue(NetworkState.LOADING)

        try{
            compositeDisposable.add(
                apiService.getMovieDet(movie_id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            downloadedMovieDetailsResponse_.postValue(it)
                            networkState_.postValue(NetworkState.LOADING)
                        },
                        {
                            networkState_.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message.toString())

                        }
                    )
            )
        }
        catch (e:Exception){
            Log.e("MovieDetailsDataSource", e.message.toString())
        }

    }

}