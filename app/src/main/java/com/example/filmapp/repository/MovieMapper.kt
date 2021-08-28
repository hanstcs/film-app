package com.example.filmapp.repository

import com.example.filmapp.Movie
import com.example.filmapp.repository.local.MovieModel as MovieModelDB

class MovieMapper {
    companion object {

        fun toMovieDBList(movies: List<Movie>) = movies.map { toMovieDB(it) }

        private fun toMovieDB(movie: Movie): MovieModelDB {
            return MovieModelDB(
                null,
                movie.title,
                movie.posterPath,
                movie.overview,
                movie.voteAverage,
                movie.voteCount
            )
        }

        fun toMovieRepoList(movies: List<MovieModelDB>) = movies.map { toMovieRepo(it) }

        private fun toMovieRepo(movie: MovieModelDB): MovieModel =
            MovieModel(
                movie.id!!,
                movie.title,
                movie.posterPath,
                movie.overview,
                movie.voteAverage,
                movie.voteCount
            )
    }
}
