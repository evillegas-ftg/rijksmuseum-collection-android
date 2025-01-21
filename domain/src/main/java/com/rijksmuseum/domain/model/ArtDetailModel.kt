package com.rijksmuseum.domain.model

data class ArtDetailModel(
    val id: String,
    val objectNumber: String,
    val title: String,
    val maker: String,
    val image: String?,
    val location: String?,
    val dating: String?,
    val description: String?
)
