package com.example.filmapp.repository

data class MovieModel(
    val id: Int,
    val title: String? = "",
    val posterPath: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int
)
