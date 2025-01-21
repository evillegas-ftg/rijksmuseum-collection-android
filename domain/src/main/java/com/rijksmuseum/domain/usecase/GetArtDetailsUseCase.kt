package com.rijksmuseum.domain.usecase

import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.domain.repository.ArtRepository
import javax.inject.Inject

class GetArtDetailsUseCase @Inject constructor(
    private val artRepository: ArtRepository,
) {
    suspend fun get(id: String): Result<ArtDetailModel> {
        return artRepository.getArtDetails(id)
    }
}
