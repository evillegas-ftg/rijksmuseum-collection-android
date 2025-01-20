package com.rijksmuseum.data.di

import com.rijksmuseum.data.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        retrofit: Retrofit
    ): RemoteDataSource {
        return retrofit.create(RemoteDataSource::class.java)
    }
}
