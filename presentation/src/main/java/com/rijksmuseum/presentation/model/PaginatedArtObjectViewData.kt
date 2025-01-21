package com.rijksmuseum.presentation.model

data class PaginatedArtObjectViewData(
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = false,
    val items: List<ArtCollectionGroupViewData>,
    val loadingMoreError: String? = null,
)
