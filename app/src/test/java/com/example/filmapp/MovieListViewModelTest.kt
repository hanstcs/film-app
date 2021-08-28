package com.example.filmapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.filmapp.repository.MovieListRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations

internal class MovieListViewModelTest {
    private lateinit var viewModel: MovieListViewModel
    private val stateObserver = mock<Observer<MovieListViewState>>()
    private val repo: MovieListRepository = mock()

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MovieListViewModel(repo)
        viewModel.state.observeForever(stateObserver)
        InstantTaskExecutorRule()
    }

    @Test
    internal fun `when fetch movie data success should set correct state`() {
        whenever(repo.getMovieListSingle())
            .thenReturn(Flowable.just(emptyList()))
        viewModel.loadMovies()

        verify(stateObserver).onChanged(MovieListViewState.ShowLoading)
        verify(stateObserver).onChanged(MovieListViewState.ShowMovies(emptyList()))
    }

    @Test
    internal fun `when fetch movie data failed should set correct state`() {
        whenever(repo.getMovieListSingle())
            .thenReturn(Flowable.error(Throwable()))
        viewModel.loadMovies()

        verify(stateObserver).onChanged(MovieListViewState.ShowLoading)
        verify(stateObserver).onChanged(MovieListViewState.Failed)
    }

    @AfterEach
    internal fun tearDown() {
        viewModel.state.removeObserver(stateObserver)
    }
}
