package com.example.filmapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filmapp.RxSchedulers
import com.example.filmapp.repository.local.AppDatabase
import com.example.filmapp.repository.local.MovieModel
import com.example.filmapp.repository.remote.Movie
import com.example.filmapp.repository.remote.MovieListResponse
import com.example.filmapp.repository.remote.MovieRemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MovieListRepositoryImplTest {
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieRepo: MovieListRepositoryImpl
    private lateinit var db: AppDatabase

    private val remoteDataSource: MovieRemoteDataSource = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        movieRepo = MovieListRepositoryImpl(remoteDataSource, db)
    }

    @Test
    fun `when service return movies should save it to database`() {
        whenever(remoteDataSource.getListSingle())
            .thenReturn(Single.just(getMovieResponse()))

        movieRepo.getMovieListSingle()

        db.movieDAO().insertAll(getMovieDBList()).test().apply {
            assertComplete()
            assertNoErrors()
        }
    }

    @Test
    fun `when service is failed, should return data from local db`() {
        val previousMovies = mutableListOf<MovieModel>().apply {
            MovieModel(3, "Previous", "asd", "Gan", 5.5, 1)
        }
        db.movieDAO().insertAll(previousMovies)

        whenever(remoteDataSource.getListSingle())
            .thenReturn(Single.error(Throwable()))

        movieRepo.getMovieListSingle()

        db.movieDAO().getAll().test().apply {
            assertComplete()
            assertNoErrors()
        }
    }

    private fun getMovieResponse(): MovieListResponse {
        val movies = mutableListOf<Movie>().apply {
            add(Movie(1, "First", "First", "Gan", "/abc-path", 8.4, 2))
            add(Movie(2, "Second", "Second", "Gan", "/abc-path", 8.4, 5))
        }
        return MovieListResponse(7.4, 1, movies)
    }

    private fun getMovieDBList(): List<MovieModel> {
        return mutableListOf<MovieModel>().apply {
            add(MovieModel(1, "First", "/abc-path", "Gan", 8.4, 2))
            add(MovieModel(2, "Second", "/abc-path", "Gan", 8.4, 5))
        }
    }

    @After
    fun tearDown() {
        db.close()
    }
}