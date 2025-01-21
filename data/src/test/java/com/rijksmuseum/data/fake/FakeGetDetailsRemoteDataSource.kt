package com.rijksmuseum.data.fake

import com.rijksmuseum.data.datasource.RemoteDataSource
import com.rijksmuseum.data.model.response.CollectionDetailResponse
import com.rijksmuseum.data.model.response.CollectionResponse

class FakeGetDetailsRemoteDataSource(
    private val expectedResponse: CollectionDetailResponse
) : RemoteDataSource {
    override suspend fun getCollection(sort: String, page: Int, pageSize: Int): CollectionResponse {
        throw NotImplementedError("This fake only implements getCollectionDetail")
    }

    override suspend fun getCollectionDetail(id: String): CollectionDetailResponse {
        return expectedResponse
    }
}
