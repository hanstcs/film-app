package com.example.filmapp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.filmapp.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepo: MovieListRepository
) : ViewModel() {
    private val addDisposable by lazy { CompositeDisposable() }

    fun loadMovies() {
        movieListRepo.getMovieListSingle()
            .subscribe(
                { response -> Log.d("Movie", response.toString()) },
                { throwable -> Log.e("Movie is failed ", throwable.message ?: "Error", throwable) }
            ).let(addDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        addDisposable.clear()
    }
}
