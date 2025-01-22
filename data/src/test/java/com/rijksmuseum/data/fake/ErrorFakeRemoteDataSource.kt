package com.rijksmuseum.data.fake

import com.rijksmuseum.data.datasource.RemoteDataSource
import com.rijksmuseum.data.model.response.CollectionDetailResponse
import com.rijksmuseum.data.model.response.CollectionResponse

internal class ErrorFakeRemoteDataSource(
    private val expectedException: Exception
) : RemoteDataSource {
    override suspend fun getCollection(sort: String, page: Int, pageSize: Int): CollectionResponse {
        throw expectedException
    }

    override suspend fun getCollectionDetail(id: String): CollectionDetailResponse {
        throw expectedException
    }
}
