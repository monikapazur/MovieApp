package com.example.movieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.Film

class FilmAdapter(private val listener: OnFilmItemLongClick) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {


    private val filmsList = ArrayList<Film>()



    fun setFilms(list: List<Film>) {
        filmsList.clear()
        filmsList.addAll(list)
        notifyDataSetChanged()
    }
    fun deleteFilm(film: Film, position: Int){
        filmsList.remove(film)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_list_item, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {


        bindData(holder)
    }

    private fun bindData(holder: FilmViewHolder) {
        val title = holder.itemView.findViewById<TextView>(R.id.card_view_movie_title)
        val date = holder.itemView.findViewById<TextView>(R.id.card_view_movie_date)
        val image = holder.itemView.findViewById<ImageView>(R.id.card_view_poster)

        title.text = filmsList[holder.adapterPosition].name
        date.text = filmsList[holder.adapterPosition].productionYear
        //image
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    inner class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {
                listener.onFilmLongClick(filmsList[adapterPosition], adapterPosition)
                true
            }
        }
    }

}

interface OnFilmItemLongClick {
    fun onFilmLongClick(film: Film, position: Int)
}
