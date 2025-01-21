package com.rijksmuseum.domain.usecase

import com.rijksmuseum.domain.model.PaginatedArtListModel
import com.rijksmuseum.domain.repository.ArtRepository
import javax.inject.Inject

class GetCollectionPageUseCase @Inject constructor(
    private val artRepository: ArtRepository,
) {
    suspend fun get(page: Int): Result<PaginatedArtListModel> {
        return artRepository.getCollection(page)
    }
}
