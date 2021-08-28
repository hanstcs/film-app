package com.example.filmapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.repository.MovieListRepository
import com.example.filmapp.repository.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepo: MovieListRepository
) : ViewModel() {
    private val _state: MutableLiveData<MovieListViewState> = MutableLiveData()
    val state: LiveData<MovieListViewState> get() = _state
    private val addDisposable by lazy { CompositeDisposable() }

    fun loadMovies() {
        movieListRepo.getMovieListSingle()
            .doOnSubscribe { _state.value = MovieListViewState.ShowLoading }
            .subscribe(
                { _state.value = MovieListViewState.ShowMovies(it) },
                { _state.value = MovieListViewState.Failed }
            ).let(addDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        addDisposable.clear()
    }
}

sealed class MovieListViewState {
    object ShowLoading : MovieListViewState()
    object Failed : MovieListViewState()

    data class ShowMovies(val movies: List<MovieModel>) : MovieListViewState()
}
