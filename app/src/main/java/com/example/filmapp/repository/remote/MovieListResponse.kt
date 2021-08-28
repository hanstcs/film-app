package com.example.filmapp.repository.remote

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("average_rating")
    val averageRating: Double,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    var results: List<Movie> = emptyList()
)

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)
