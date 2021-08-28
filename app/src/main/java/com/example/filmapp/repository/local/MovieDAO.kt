package com.example.filmapp.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmapp.repository.local.Constant.MOVIES_TABLE_NAME
import io.reactivex.Single

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieModel>): Single<List<Long>>

    @Query("SELECT * FROM $MOVIES_TABLE_NAME")
    fun getAll(): Single<List<MovieModel>>
}
