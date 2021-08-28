package com.example.filmapp.injection

import com.example.filmapp.repository.MovieListRepository
import com.example.filmapp.repository.MovieListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieListRepo(
        binds: MovieListRepositoryImpl
    ): MovieListRepository
}
