package com.rijksmuseum.presentation.mapper

import com.rijksmuseum.domain.model.ArtObjectListModel
import com.rijksmuseum.presentation.model.ArtCollectionGroupViewData
import com.rijksmuseum.presentation.model.ArtCollectionItemViewData

fun List<ArtObjectListModel>.toViewData(): List<ArtCollectionGroupViewData> {
    if (isEmpty()) return emptyList()

    return fold(mutableListOf()) { acc, item ->
        val newItem = ArtCollectionItemViewData(
            id = item.objectNumber,
            title = item.title,
            image = item.image,
        )

        if (acc.isEmpty() || acc.last().artist != item.maker) {
            acc.add(
                ArtCollectionGroupViewData(
                    artist = item.maker,
                    items = listOf(newItem)
                )
            )
        } else {
            val lastGroup = acc.last()
            acc[acc.lastIndex] = lastGroup.copy(items = lastGroup.items + listOf(newItem))
        }
        acc
    }
}
