package com.rijksmuseum.data.datasource

import androidx.annotation.IntRange
import com.rijksmuseum.data.model.response.CollectionDetailResponse
import com.rijksmuseum.data.model.response.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDataSource {

    companion object {
        private const val COLLECTION_DEFAULT_SORT = "artist"
    }

    @GET("collection")
    suspend fun getCollection(
        @Query("s") sort: String = COLLECTION_DEFAULT_SORT,
        @Query("p") @IntRange(from = 0) page: Int,
        @Query("ps") @IntRange(from = 1, to = 100) pageSize: Int,
    ): CollectionResponse

    @GET("collection/{collection_id}")
    suspend fun getCollectionDetail(
        @Path("collection_id") id: String,
    ): CollectionDetailResponse
}
