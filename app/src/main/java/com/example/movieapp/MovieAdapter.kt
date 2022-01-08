package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.o.MovieDetails
import com.example.movieapp.data.api.POSTER_BASE_URL

class MovieAdapter(): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val moviesList = ArrayList<MovieDetails>()

    fun setFavMovies(list: List<MovieDetails>){
        moviesList.clear()
        moviesList.addAll(list)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val title = holder.itemView.findViewById<TextView>(R.id.card_view_movie_title)
        val date = holder.itemView.findViewById<TextView>(R.id.card_view_movie_date)
        val image = holder.itemView.findViewById<ImageView>(R.id.card_view_poster)

        title.text = moviesList[holder.adapterPosition].title
        date.text = moviesList[holder.adapterPosition].releaseDate
        val moviePosterURL = POSTER_BASE_URL + moviesList[holder.adapterPosition].posterPath
        Glide.with(holder.itemView.context)
            .load(moviePosterURL)
            .into(image)

    }

    override fun getItemCount(): Int {
       return moviesList.size
    }

    inner class MovieViewHolder(view: View): RecyclerView.ViewHolder(view){
        init{
            view.setOnClickListener {
               /* listener.onMovieLongClickListener()*/
            }
        }
    }
}
interface OnMovieItemLongClick {
    fun onMovieLongClick(movie: MovieDetails, position: Int)
}
