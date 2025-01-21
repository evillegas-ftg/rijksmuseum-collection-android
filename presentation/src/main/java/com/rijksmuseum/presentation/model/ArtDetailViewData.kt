package com.rijksmuseum.presentation.model

data class ArtDetailViewData(
    val objectNumber: String,
    val title: String,
    val maker: String,
    val image: String?,
    val location: String?,
    val dating: String?,
    val description: String?
)
