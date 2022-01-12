package com.example.movieapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.o.Video
import com.example.movieapp.data.o.VideoResponse
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class VideoViewModel(private val videoRepo: VideoRepo, movieId: Int) :ViewModel(){

    val videoResponse : MutableLiveData<VideoResponse> = MutableLiveData()

   fun getVideo(movieId: Int){

        viewModelScope.launch {
            val response = videoRepo.getVideos(movieId)
            videoResponse.value = response
        }
   }

}