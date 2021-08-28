package com.example.filmapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.filmapp.databinding.ItemMovieBinding
import com.example.filmapp.repository.MovieModel

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun inflate(parent: ViewGroup): MovieViewHolder =
            MovieViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    fun bind(movie: MovieModel) {
        val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
        val url = imageBaseUrl + movie.posterPath
        Glide.with(binding.root.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivMoviePoster)
    }

    fun setActionListener(listener: (position: Int) -> Unit) {
        binding.root.setOnClickListener {
            listener.invoke(adapterPosition)
        }
    }
}
