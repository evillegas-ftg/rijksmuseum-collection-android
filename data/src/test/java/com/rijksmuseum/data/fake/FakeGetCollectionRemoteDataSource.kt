package com.rijksmuseum.data.fake

import com.rijksmuseum.data.datasource.RemoteDataSource
import com.rijksmuseum.data.model.response.CollectionDetailResponse
import com.rijksmuseum.data.model.response.CollectionResponse

class FakeGetCollectionRemoteDataSource(
    private val expectedResponse: CollectionResponse
) : RemoteDataSource {
    override suspend fun getCollection(sort: String, page: Int, pageSize: Int): CollectionResponse {
        return expectedResponse
    }

    override suspend fun getCollectionDetail(id: String): CollectionDetailResponse {
        throw NotImplementedError("This fake only implements getCollection")
    }
}
