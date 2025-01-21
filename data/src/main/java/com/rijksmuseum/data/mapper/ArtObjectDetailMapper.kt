package com.rijksmuseum.data.mapper

import com.rijksmuseum.data.model.response.ArtDetailObjectResponse
import com.rijksmuseum.domain.model.ArtDetailModel

fun ArtDetailObjectResponse.toDomain(): ArtDetailModel {
    return ArtDetailModel(
        id = id,
        objectNumber = objectNumber,
        title = title,
        maker = principalOrFirstMaker,
        image = webImage?.let { image ->
            image.url?.takeIf { image.guid != null }
        },
        location = location,
        dating = dating?.presentingDate,
        description = description,
    )
}
