package com.rijksmuseum.presentation.mapper

import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.presentation.model.ArtDetailViewData

internal fun ArtDetailModel.toViewData(): ArtDetailViewData {
    return ArtDetailViewData(
        objectNumber = objectNumber,
        title = title,
        maker = maker,
        image = image,
        location = location,
        dating = dating,
        description = description,
    )
}
