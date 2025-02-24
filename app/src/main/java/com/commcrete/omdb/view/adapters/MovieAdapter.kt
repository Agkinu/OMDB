package com.commcrete.omdb.view.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commcrete.omdb.R
import com.commcrete.omdb.model.MovieItem

class MovieAdapter(private val clickListener: (MovieItem) -> Unit) :
    ListAdapter<MovieItem, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { clickListener(movie) }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val poster: ImageView = view.findViewById(R.id.movie_poster)
        private val title: TextView = view.findViewById(R.id.movie_title)
        private val year: TextView = view.findViewById(R.id.movie_year)

        fun bind(movie: MovieItem) {
            title.text = movie.title
            year.text = movie.year
            Glide.with(itemView.context).load(movie.poster).into(poster)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem) = oldItem.imdbID == newItem.imdbID
            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem) = oldItem == newItem
        }
    }
}

