package com.example.movieapp.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.api.MovieDBInterface
import com.example.movieapp.data.o.Video
import io.reactivex.disposables.CompositeDisposable

class VideoDataSource(private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable) {

   private val networkState_ = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
    get() = networkState_

    private val videoResponse_ = MutableLiveData<Video>()
    val videoResponse: LiveData<Video>
    get() = videoResponse_


}