package com.example.filmapp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.volley.VolleyError
import com.example.filmapp.repository.MovieRemoteDataSource
import com.example.filmapp.repository.NetworkCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val dataSource: MovieRemoteDataSource
) : ViewModel() {

    fun loadMovies() {
        dataSource.getList(1,
            object : NetworkCallback<MovieListResponse> {
                override fun onSuccess(response: MovieListResponse) {
                    Log.d("Movie", response.toString())
                }

                override fun onError(error: VolleyError) {
                    Log.e("Movie", error.message ?: "Error")
                }
            })
    }
}
