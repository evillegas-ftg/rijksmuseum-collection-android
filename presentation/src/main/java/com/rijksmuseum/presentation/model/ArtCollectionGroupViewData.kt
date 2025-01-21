package com.rijksmuseum.presentation.model

data class ArtCollectionGroupViewData(
    val artist: String,
    val items: List<ArtCollectionItemViewData>,
)

data class ArtCollectionItemViewData(
    val id: String,
    val title: String,
    val image: String?,
)
