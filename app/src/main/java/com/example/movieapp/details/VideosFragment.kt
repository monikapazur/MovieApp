package com.example.movieapp.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class VideosFragment : Fragment(), YouTubePlayer.OnInitializedListener {

    val video_key = "Jd3nTm-wvyA"
    val yt_api_key = "AIzaSyAkVRmEOTJFekFc1NYsyLHRW5sjlQ9Cl_A"


    private lateinit var ytPlayer : YouTubePlayerView
    private lateinit var btnPlay : Button

    lateinit var youTubePlayerInit : YouTubePlayer.OnInitializedListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_details, container, false )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnPlay.setOnClickListener {
            ytPlayer.initialize(yt_api_key,youTubePlayerInit)
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.loadVideo(video_key)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Toast.makeText(context, "feeejl", Toast.LENGTH_SHORT).show()
    }

}