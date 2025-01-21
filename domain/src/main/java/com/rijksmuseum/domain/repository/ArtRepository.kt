package com.rijksmuseum.domain.repository

import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.domain.model.PaginatedArtListModel

interface ArtRepository {
    suspend fun getCollection(page: Int): Result<PaginatedArtListModel>

    suspend fun getArtDetails(id: String): Result<ArtDetailModel>
}
