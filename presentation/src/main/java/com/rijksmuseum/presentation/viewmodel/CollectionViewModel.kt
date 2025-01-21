package com.rijksmuseum.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijksmuseum.domain.model.PaginatedArtListModel
import com.rijksmuseum.domain.usecase.GetCollectionPageUseCase
import com.rijksmuseum.presentation.mapper.toViewData
import com.rijksmuseum.presentation.model.ArtCollectionGroupViewData
import com.rijksmuseum.presentation.model.PaginatedArtObjectViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollectionPage: GetCollectionPageUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionState())
    val uiState = _uiState.asStateFlow()

    private var nextPage: Int = 0

    init {
        loadNextPage()
    }

    fun onMoreItemsRequested() {
        loadNextPage()
    }

    fun onRetryClicked() {
        loadNextPage()
    }

    private fun loadNextPage() {
        if (!_uiState.value.canLoad) return

        _uiState.update { current ->
            if (nextPage == 0) {
                CollectionState(loading = true)
            } else {
                current.copy(
                    data = current.data.copy(
                        isLoadingMore = true,
                        loadingMoreError = null,
                    )
                )
            }
        }

        viewModelScope.launch {
            getCollectionPage.get(page = nextPage)
                .fold(
                    onSuccess = ::onPageLoadSuccess,
                    onFailure = ::onPageLoadFailure,
                )
        }
    }

    private fun onPageLoadSuccess(newData: PaginatedArtListModel) {
        _uiState.update { current ->
            nextPage = newData.nextPageIndex
            current.copy(
                data = current.data.copy(
                    items = mergeItems(
                        oldItems = current.data.items,
                        newItems = newData.items.toViewData(),
                    ),
                    isLoadingMore = false,
                    canLoadMore = newData.hasMoreItems
                ),
                loading = false,
            )
        }
    }

    private fun onPageLoadFailure(error: Throwable) {
        _uiState.update { current ->
            if (nextPage == 0) {
                current.copy(
                    loading = false,
                    error = error.message.orEmpty(),
                )
            } else {
                current.copy(
                    data = current.data.copy(
                        isLoadingMore = false,
                        canLoadMore = false,
                        loadingMoreError = error.message.orEmpty(),
                    )
                )
            }
        }
    }

    private fun mergeItems(
        oldItems: List<ArtCollectionGroupViewData>,
        newItems: List<ArtCollectionGroupViewData>
    ): List<ArtCollectionGroupViewData> {
        if (oldItems.isEmpty()) return newItems
        if (newItems.isEmpty()) return oldItems

        val mergedItems = oldItems.toMutableList()

        val lastOldItem = oldItems.last()
        val firstNewItem = newItems.first()

        if (lastOldItem.artist == firstNewItem.artist) {
            val combinedItems = lastOldItem.items + firstNewItem.items
            mergedItems[mergedItems.lastIndex] = lastOldItem.copy(items = combinedItems)
            mergedItems.addAll(newItems.drop(1))
        } else {
            mergedItems.addAll(newItems)
        }

        return mergedItems
    }
}

data class CollectionState(
    val loading: Boolean = false,
    val data: PaginatedArtObjectViewData = PaginatedArtObjectViewData(items = emptyList()),
    val error: String? = null
) {
    val canLoad: Boolean = !loading && !data.isLoadingMore
}
