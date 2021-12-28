package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.data.popular_movie.MainActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    private val fbAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    override fun onStart() {
        super.onStart()
        isCurrentUser()
    }

    private fun isCurrentUser() {
        fbAuth.currentUser?.let { auth ->
            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }
}