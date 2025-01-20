package com.rijksmuseum.domain.repository

import com.rijksmuseum.domain.model.PaginatedArtListModel

interface ArtRepository {
    suspend fun getCollection(page: Int) : Result<PaginatedArtListModel>
}
