package com.example.movieapp.data.popular_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.example.movieapp.data.o.PopularMovies
import com.example.movieapp.data.repo.NetworkState
import com.example.movieapp.details.SingleDetails

class PopularMoviePagedListAdapter(public val context: Context) :
    PagedListAdapter<PopularMovies, RecyclerView.ViewHolder>(PopularMoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
        return MovieItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val title = holder.itemView.findViewById<TextView>(R.id.card_view_movie_title)
        val date = holder.itemView.findViewById<TextView>(R.id.card_view_movie_date)
        val image = holder.itemView.findViewById<ImageView>(R.id.card_view_poster)

        title.text = getItem(position)?.title
        date.text = getItem(position)?.releaseDate
        val moviePosterURL = POSTER_BASE_URL + getItem(position)?.posterPath
        Glide.with(holder.itemView.context)
            .load(moviePosterURL)
            .into(image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleDetails::class.java)
            intent.putExtra("id", getItem(position)?.id)
            context.startActivity(intent)
        }
    }

    class PopularMoviesDiffCallback : DiffUtil.ItemCallback<PopularMovies>() {
        override fun areItemsTheSame(oldItem: PopularMovies, newItem: PopularMovies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularMovies, newItem: PopularMovies): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: PopularMovies?, context: Context) {

        }
    }


}