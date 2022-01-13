package com.example.movieapp.data.repo

import android.util.Log
import androidx.lifecycle.*
import com.example.movieapp.data.Film
import com.example.movieapp.data.User
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.details.MovieDetailsRepo
import com.example.movieapp.details.SingleMovieViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseRepo {
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val cloud = FirebaseFirestore.getInstance()
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepo

    fun getUserData(): LiveData<User> {
        val cloudResult = MutableLiveData<User>()
        val uid = auth.currentUser?.uid
        cloud.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user)
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
        return cloudResult
    }

    fun getFavMovies(list: List<Int?>?):MutableLiveData<List<Int?>>{
        val cloudResult = MutableLiveData<List<Int?>>()
        val uid = auth.currentUser?.uid
        cloud.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user!!.favFilms)
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }

        return cloudResult
    }
    fun getWatchedMovies(list: List<Int?>?):MutableLiveData<List<Int?>>{
        val cloudResult = MutableLiveData<List<Int?>>()
        val uid = auth.currentUser?.uid
        cloud.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user!!.watchedMovie)
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }

        return cloudResult
    }
    fun getToWatchMovies(list: List<Int?>?):MutableLiveData<List<Int?>>{
        val cloudResult = MutableLiveData<List<Int?>>()
        val uid = auth.currentUser?.uid
        cloud.collection("users")
            .document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user!!.toWatchMovie)
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }

        return cloudResult
    }


    fun getMovies(): LiveData<List<Film>> {
        val cloudResult = MutableLiveData<List<Film>>()

        cloud.collection("Films")
            .get()
            .addOnSuccessListener {
                val film = it.toObjects(Film::class.java)
                cloudResult.postValue(film)
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
        return cloudResult
    }

    fun addFavFilm(film: Film) {
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("favFilms", FieldValue.arrayUnion(film.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "Dodana do ulub")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
    fun addFavMovie(movie: MovieDetails){
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("favFilms", FieldValue.arrayUnion(movie.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "movie dodany do ulub")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
    fun addToWatchMovie(movie: MovieDetails){
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("toWatchMovie", FieldValue.arrayUnion(movie.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "movie dodany do do obejrzenia")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
    fun addToWatchedMovie(movie: MovieDetails){
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("watchedMovie", FieldValue.arrayUnion(movie.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "movie dodany do obejrzanych")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }

    fun deleteFavMovies(movie: MovieDetails) {
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("favFilms", FieldValue.arrayRemove(movie.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "Usunieto z ulub")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
    fun deleteWatchedMovies(movie: MovieDetails) {
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("watchedMovie", FieldValue.arrayRemove(movie.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "Usunieto z ulub")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
    fun deleteToWatchMovies(movie: MovieDetails) {
        cloud.collection("users").document(auth.currentUser?.uid!!)
            .update("toWatchMovie", FieldValue.arrayRemove(movie.id))
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "Usunieto z ulub")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
    fun getFavFilms(list: List<String?>?): LiveData<List<Film>> {
        val cloudResult = MutableLiveData<List<Film>>()

        if (!list.isNullOrEmpty()) {
            cloud.collection("Films")
                .whereIn("id", list)
                .get()
                .addOnSuccessListener {
                    val resultList = it.toObjects(Film::class.java)
                    cloudResult.postValue(resultList)

                }
                .addOnFailureListener {
                    Log.d("REPO_DEBUG", it.message.toString())
                }
        }
        return cloudResult
    }



    fun createNewUser(user: User){
        cloud.collection("users")
            .document(user.uid!!)
            .set(user)
    }

    fun editProfileData(map: Map<String, String>){
        cloud.collection("users")
            .document(auth.currentUser!!.uid)
            .update(map)
            .addOnSuccessListener {
                Log.d("REPO_DEBUG", "zmiana danych")
            }
            .addOnFailureListener {
                Log.d("REPO_DEBUG", it.message.toString())
            }
    }
}