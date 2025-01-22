package com.rijksmuseum.data.mapper

import com.rijksmuseum.data.model.response.ArtObjectResponse
import com.rijksmuseum.domain.model.ArtObjectListModel

internal fun ArtObjectResponse.toDomain(): ArtObjectListModel {
    return ArtObjectListModel(
        id = id,
        objectNumber = objectNumber,
        title = title,
        maker = principalOrFirstMaker,
        image = webImage?.let { image ->
            image.url?.takeIf { image.guid != null }
        }
    )
}
