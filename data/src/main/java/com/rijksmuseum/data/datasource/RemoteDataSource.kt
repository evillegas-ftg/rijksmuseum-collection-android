package com.rijksmuseum.data.datasource

import androidx.annotation.IntRange
import com.rijksmuseum.data.model.response.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {

    companion object {
        private const val COLLECTION_DEFAULT_PAGE_SIZE = 50
        private const val COLLECTION_DEFAULT_SORT = "artist"
    }

    @GET("collection")
    suspend fun getCollection(
        @Query("ps") pageSize: Int = COLLECTION_DEFAULT_PAGE_SIZE,
        @Query("s") sort: String = COLLECTION_DEFAULT_SORT,
        @Query("p") @IntRange(from = 0) page: Int,
    ): CollectionResponse
}
