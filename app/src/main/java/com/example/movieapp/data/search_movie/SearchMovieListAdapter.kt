package com.example.movieapp.data.search_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.example.movieapp.data.o.SearchMovie
import com.example.movieapp.details.SingleDetails

class SearchMovieListAdapter(val context: Context) :
    RecyclerView.Adapter<SearchMovieListAdapter.ViewHolder>() {

    private val searchMovieList = ArrayList<SearchMovie>()

    fun setSearchMovies(list: List<SearchMovie>) {
        searchMovieList.clear()
        searchMovieList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDate: TextView

        init {
            itemImage = itemView.findViewById(R.id.card_view_poster)
            itemTitle = itemView.findViewById(R.id.card_view_movie_title)
            itemDate = itemView.findViewById(R.id.card_view_movie_date)
        }
    }

    /* override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



         title.text = getItem(position)?.title
         date.text = getItem(position)?.releaseDate
         val moviePosterURL = POSTER_BASE_URL + getItem(position)?.posterPath
         Glide.with(holder.itemView.context)
             .load(moviePosterURL)
             .into(image)


         }
     }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_search_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchMovieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = searchMovieList[holder.adapterPosition].title
        holder.itemDate.text = searchMovieList[holder.adapterPosition].releaseDate
        val moviePosterURL = POSTER_BASE_URL + searchMovieList[holder.adapterPosition].posterPath
        Glide.with(holder.itemView.context)
            .load(moviePosterURL)
            .into(holder.itemImage)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleDetails::class.java)
            intent.putExtra("id", searchMovieList[holder.adapterPosition]?.id)
            context.startActivity(intent)
        }

    }
}