package com.rijksmuseum.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CollectionResponse(
    @SerialName("count") val count: Int,
    @SerialName("artObjects") val artObjects: List<ArtObjectResponse>
)

@Serializable
internal data class ArtObjectResponse(
    @SerialName("id") val id: String,
    @SerialName("objectNumber") val objectNumber: String,
    @SerialName("title") val title: String,
    @SerialName("principalOrFirstMaker") val principalOrFirstMaker: String,
    @SerialName("webImage") val webImage: WebImageResponse?,
)
