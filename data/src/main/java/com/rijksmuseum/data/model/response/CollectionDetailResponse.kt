package com.rijksmuseum.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionDetailResponse(
    @SerialName("artObject") val artObject: ArtDetailObjectResponse
)

@Serializable
data class ArtDetailObjectResponse(
    @SerialName("id") val id: String,
    @SerialName("objectNumber") val objectNumber: String,
    @SerialName("title") val title: String,
    @SerialName("principalOrFirstMaker") val principalOrFirstMaker: String,
    @SerialName("webImage") val webImage: WebImageResponse?,
    @SerialName("dating") val dating: DatingResponse?,
    @SerialName("location") val location: String?,
    @SerialName("description") val description: String?,
)
