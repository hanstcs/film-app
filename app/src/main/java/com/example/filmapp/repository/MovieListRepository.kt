package com.example.filmapp.repository

import com.example.filmapp.repository.local.AppDatabase
import com.example.filmapp.repository.remote.MovieRemoteDataSource
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.example.filmapp.repository.local.MovieModel as MovieModelDB

interface MovieListRepository {
    fun getMovieListSingle(): Flowable<List<MovieModel>>
}

class MovieListRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val db: AppDatabase
) : MovieListRepository {

    override fun getMovieListSingle(): Flowable<List<MovieModel>> {
        val fromLocal = db.movieDAO().getAll()
            .subscribeOn(Schedulers.io())
            .map { MovieMapper.toMovieRepoList(it) }

        val getFromRemote = remoteDataSource.getListSingle()
            .map { MovieMapper.toMovieDBList(it.results) }
            .flatMap { saveToDB(it) }
            .flatMap { fromLocal }

        return Single.concat(fromLocal, getFromRemote)
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun saveToDB(data: List<MovieModelDB>): Single<List<Long>> {
        return db.movieDAO().insertAll(data).subscribeOn(Schedulers.io())
    }
}
