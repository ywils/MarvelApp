package com.android.marvelapp.di

import com.android.marvelapp.data.network.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideEmployeeApi(
        retrofitBuilder: Retrofit.Builder
    ) : MarvelApi {
        return retrofitBuilder
            .build()
            .create(MarvelApi::class.java)
    }

}