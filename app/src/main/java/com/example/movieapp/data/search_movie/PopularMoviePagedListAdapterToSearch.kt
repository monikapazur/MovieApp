package com.example.movieapp.data.search_movie

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
import com.example.movieapp.data.o.PopularMovies
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.example.movieapp.data.repo.NetworkState
import com.example.movieapp.details.SingleDetails

class PopularMoviePagedListAdapterToSearch(public val context: Context): PagedListAdapter<PopularMovies, RecyclerView.ViewHolder>(
    PopularMoviesDiffCallback()
) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View


        //if(viewType == MOVIE_VIEW_TYPE){
        view = layoutInflater.inflate(R.layout.movie_search_list_item, parent, false)
        return MovieItemViewHolder(view)

        //}
        //else{
        /*view = layoutInflater.inflate(R.layout.network_state_ite, parent, false)
        return NetworkStateItemViewHolder(view)*/

        //}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // if(getItemViewType(position) == MOVIE_VIEW_TYPE){
        //(holder as MovieItemViewHolder).bind(getItem(position),context)
        //}
        /*else{

        }*/

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
            intent.putExtra("id",getItem(position)?.id)
            context.startActivity(intent)
        }
    }

    private fun hasExtraRow(): Boolean{
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0

    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount - 1){
            NETWORK_VIEW_TYPE
        }else{
            MOVIE_VIEW_TYPE
        }
    }


    class PopularMoviesDiffCallback : DiffUtil.ItemCallback<PopularMovies>(){
        override fun areItemsTheSame(oldItem: PopularMovies, newItem: PopularMovies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularMovies, newItem: PopularMovies): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(movie: PopularMovies?, context: Context){

        }
    }

    /*class NetworkStateItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(networkState: NetworkState?){
            if(networkState != null && networkState == NetworkState.LOADED){
                itemView
            }
        }
    }*/

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = networkState
        val hasExtraRow = hasExtraRow()

        if(hadExtraRow != hasExtraRow){
            if(hadExtraRow){//hadExtraRow is true and hosExtraRow false
                notifyItemRemoved(super.getItemCount()) // remove the progressbar at the end
            }
            else{ //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())//add the progressbar at the end
            }
        }
        else if (hasExtraRow && previousState != newNetworkState){ //hasExtraRow is true and hadExtraRow true
            notifyItemChanged(itemCount - 1)
        }
    }

}