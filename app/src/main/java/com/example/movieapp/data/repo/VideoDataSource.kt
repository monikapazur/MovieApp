package com.example.movieapp.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.Video
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VideoDataSource(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val networkState_ = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = networkState_

    private val videoResponse_ = MutableLiveData<Video>()
    val videoResponse: LiveData<Video>
        get() = videoResponse_

    /*fun fetchVideos(movie_id: Int) {
        try {
            compositeDisposable.add(
                apiService.getVideo(movie_id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            videoResponse_.postValue(it[0])
                            networkState_.postValue(NetworkState.LOADING)
                        },
                        {
                            networkState_.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message.toString())

                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDetailsDataSource", e.message.toString())
        }
    }*/
}