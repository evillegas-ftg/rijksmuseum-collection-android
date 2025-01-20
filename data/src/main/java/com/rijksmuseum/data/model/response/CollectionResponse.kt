package com.rijksmuseum.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResponse(
    @SerialName("count") val count: Int,
    @SerialName("artObjects") val artObjects: List<ArtObjectResponse>
)

@Serializable
data class ArtObjectResponse(
    @SerialName("id") val id: String,
    @SerialName("objectNumber") val objectNumber: String,
    @SerialName("title") val title: String,
    @SerialName("principalOrFirstMaker") val principalOrFirstMaker: String,
    @SerialName("webImage") val webImage: WebImageResponse?,
)

@Serializable
data class WebImageResponse(
    @SerialName("guid") val guid: String?,
    @SerialName("url") val url: String?,
)
