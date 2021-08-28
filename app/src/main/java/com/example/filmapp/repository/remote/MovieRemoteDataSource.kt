package com.example.filmapp.repository.remote

import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.example.filmapp.GsonRequest
import com.example.filmapp.MovieListResponse
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import javax.inject.Inject

interface MovieRemoteDataSource {
    fun getListSingle(): Single<MovieListResponse>
}

class MovieRemoteDataSourceImpl @Inject constructor(
    private val requestQueue: RequestQueue
) : MovieRemoteDataSource {

    override fun getListSingle(): Single<MovieListResponse> {
        val endpoint = "$URL/list/7105016?api_key=$API_KEY&page=1"
        val result = Single.create(SingleOnSubscribe<MovieListResponse> {
            val request = GsonRequest(
                endpoint,
                MovieListResponse::class.java,
                null,
                { response -> it.onSuccess(response) },
                { error -> it.onError(error) }
            )
            requestQueue.add(request)
        })
        return result
    }

    companion object {
        private const val URL = "https://api.themoviedb.org/4"
        private const val API_KEY = "0833baf9fe4035c6c4bf8e884ca64481"
    }
}
