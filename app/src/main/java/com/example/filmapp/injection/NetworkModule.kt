package com.example.filmapp.injection

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.filmapp.repository.remote.MovieRemoteDataSource
import com.example.filmapp.repository.remote.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideVolleyRequestQueue(@ApplicationContext context: Context) =
        Volley.newRequestQueue(context)

    @Provides
    fun provideMovieRemoteDataSource(requestQueue: RequestQueue): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(requestQueue)
}
