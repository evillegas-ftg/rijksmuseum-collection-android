package com.rijksmuseum.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WebImageResponse(
    @SerialName("guid") val guid: String?,
    @SerialName("url") val url: String?,
)
