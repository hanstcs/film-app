package com.example.filmapp.repository

import com.android.volley.RequestQueue
import com.example.filmapp.GsonRequest
import com.example.filmapp.MovieListResponse
import javax.inject.Inject

interface MovieRemoteDataSource {
    fun getList(page: Int, callback: NetworkCallback<MovieListResponse>)
}

class MovieRemoteDataSourceImpl @Inject constructor(
    private val requestQueue: RequestQueue
) : MovieRemoteDataSource {

    override fun getList(page: Int, callback: NetworkCallback<MovieListResponse>) {
        val endpoint = "$URL/list/7105016?api_key=$API_KEY&page=$page"
        val request = GsonRequest(
            endpoint,
            MovieListResponse::class.java,
            null,
            { response -> callback.onSuccess(response) },
            { error -> callback.onError(error) }
        )
        requestQueue.add(request)
    }

    companion object {
        private const val URL = "https://api.themoviedb.org/4"
        private const val API_KEY = "0833baf9fe4035c6c4bf8e884ca64481"
    }
}