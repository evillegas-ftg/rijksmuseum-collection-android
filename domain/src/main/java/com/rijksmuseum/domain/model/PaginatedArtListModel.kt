package com.rijksmuseum.domain.model

sealed class PaginatedArtListModel {

    data object Uninitialised: PaginatedArtListModel()

    data object Loading: PaginatedArtListModel()

    data class Loaded(
        val items: List<ArtObjectListModel> = emptyList(),
        val hasMoreItems: Boolean = true,
        val nextPageIndex: Int = 0,
        val nextPageState: NextPageLoadingModel = NextPageLoadingModel.Idle
    )

    sealed class NextPageLoadingModel {
        data object Idle : NextPageLoadingModel()
        data object Loading : NextPageLoadingModel()
        data object Error : NextPageLoadingModel()
    }
}
