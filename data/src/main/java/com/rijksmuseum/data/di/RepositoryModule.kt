package com.rijksmuseum.data.di

import com.rijksmuseum.data.datasource.RemoteDataSource
import com.rijksmuseum.data.repository.ArtRepositoryImpl
import com.rijksmuseum.domain.repository.ArtRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideArtRepository(
        remoteDataSource: RemoteDataSource
    ): ArtRepository {
        return ArtRepositoryImpl(
            remoteDataSource = remoteDataSource,
        )
    }
}
