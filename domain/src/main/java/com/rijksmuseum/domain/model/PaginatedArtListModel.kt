package com.rijksmuseum.domain.model

data class PaginatedArtListModel(
    val items: List<ArtObjectListModel> = emptyList(),
    val hasMoreItems: Boolean = true,
    val nextPageIndex: Int = 0,
)
