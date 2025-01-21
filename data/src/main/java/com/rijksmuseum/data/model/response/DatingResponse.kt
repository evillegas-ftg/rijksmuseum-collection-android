package com.rijksmuseum.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatingResponse(
    @SerialName("presentingDate") val presentingDate: String?,
)
