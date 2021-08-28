package com.example.filmapp.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
}
