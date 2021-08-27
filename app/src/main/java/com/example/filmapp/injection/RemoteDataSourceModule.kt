package com.example.filmapp.injection

import com.example.filmapp.repository.MovieRemoteDataSource
import com.example.filmapp.repository.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun provideMovieRemoteDataSource(
        binds: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource
}