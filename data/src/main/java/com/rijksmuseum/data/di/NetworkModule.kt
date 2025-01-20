package com.rijksmuseum.data.di

import com.rijksmuseum.data.BuildConfig
import com.rijksmuseum.data.util.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @NetworkBaseUrl
    fun provideNetworkBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    @ApiAuthKey
    fun provideApiAuthKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(
        @ApiAuthKey key: String,
    ) : ApiKeyInterceptor {
        return ApiKeyInterceptor(key)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        @NetworkBaseUrl baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()
    }
}
