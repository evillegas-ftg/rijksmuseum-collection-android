package com.rijksmuseum.domain.model

data class ArtObjectListModel(
    val id: String,
    val objectNumber: String,
    val title: String,
    val maker: String,
    val image: String?,
)
