package com.rijksmuseum.data.util.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor(
    private val key: String
) : Interceptor {

    companion object {
        private const val API_TOKEN_KEY = "key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequestUrl = chain.request().url().newBuilder()
            .addQueryParameter(API_TOKEN_KEY, key)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newRequestUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
