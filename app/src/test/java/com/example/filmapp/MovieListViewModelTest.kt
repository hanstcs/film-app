package com.example.filmapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.filmapp.repository.MovieListRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

class MovieListViewModelTest {
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieListViewModel
    private val stateObserver = mock<Observer<MovieListViewState>>()
    private val repo: MovieListRepository = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MovieListViewModel(repo)

        RxSchedulers.start()
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun `when fetch movie data success should set correct state`() {
        whenever(repo.getMovieListSingle())
            .thenReturn(Flowable.just(emptyList()))
        viewModel.loadMovies()

        verify(stateObserver).onChanged(MovieListViewState.ShowLoading)
        verify(stateObserver).onChanged(MovieListViewState.ShowMovies(emptyList()))
    }

    @Test
    fun `when fetch movie data failed should set correct state`() {
        whenever(repo.getMovieListSingle())
            .thenReturn(Flowable.error(Throwable()))
        viewModel.loadMovies()

        verify(stateObserver).onChanged(MovieListViewState.ShowLoading)
        verify(stateObserver).onChanged(MovieListViewState.Failed)
    }

    @After
    fun tearDown() {
        RxSchedulers.tearDown()
        viewModel.state.removeObserver(stateObserver)
    }
}
