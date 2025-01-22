package com.rijksmuseum.presentation.viewmodel

import com.rijksmuseum.domain.model.PaginatedArtListModel
import com.rijksmuseum.domain.usecase.GetCollectionPageUseCase
import com.rijksmuseum.presentation.model.PaginatedArtObjectViewData
import com.rijksmuseum.presentation.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class CollectionViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getCollectionPageUseCase = mockk<GetCollectionPageUseCase>(relaxed = true)

    @Test
    fun `when initialised then first page is fetched`() = runTest {
        val firstPageResult = Result.success(
            PaginatedArtListModel(
                items = emptyList(),
                hasMoreItems = true,
                nextPageIndex = 1,
            )
        )
        coEvery { getCollectionPageUseCase.get(eq(1)) } returns firstPageResult

        CollectionViewModel(
            getCollectionPage = getCollectionPageUseCase
        )

        advanceUntilIdle()

        coVerify { getCollectionPageUseCase.get(eq(1)) }
    }

    @Test
    fun `when load more items then next page is requested`() = runTest {
        val firstPageResult = Result.success(
            PaginatedArtListModel(
                items = emptyList(),
                hasMoreItems = true,
                nextPageIndex = 2,
            )
        )
        val secondPageResult = Result.success(
            PaginatedArtListModel(
                items = emptyList(),
                hasMoreItems = false,
                nextPageIndex = 3,
            )
        )
        coEvery { getCollectionPageUseCase.get(eq(1)) } returns firstPageResult
        coEvery { getCollectionPageUseCase.get(eq(2)) } returns secondPageResult

        val viewModel = CollectionViewModel(
            getCollectionPage = getCollectionPageUseCase
        )

        advanceUntilIdle()
        coVerify { getCollectionPageUseCase.get(eq(1)) }
        viewModel.onMoreItemsRequested()
        advanceUntilIdle()
        coVerify { getCollectionPageUseCase.get(eq(2)) }
    }

    @Test
    fun `when a page fail to load and retry is clicked then same page is fetched`() = runTest {
        val firstPageResult = Result.failure<PaginatedArtListModel>(
            Exception("No internet")
        )
        coEvery { getCollectionPageUseCase.get(eq(1)) } returns firstPageResult

        val viewModel = CollectionViewModel(
            getCollectionPage = getCollectionPageUseCase
        )

        advanceUntilIdle()
        viewModel.onRetryClicked()
        advanceUntilIdle()

        coVerify(exactly = 2) { getCollectionPageUseCase.get(eq(1)) }
    }

    @Test
    fun `when loading first page then state is updated correctly`() = runTest {
        val firstPageResult = Result.success(
            PaginatedArtListModel(
                items = emptyList(),
                hasMoreItems = true,
                nextPageIndex = 2,
            )
        )
        coEvery { getCollectionPageUseCase.get(eq(1)) } returns firstPageResult

        val viewModel = CollectionViewModel(
            getCollectionPage = getCollectionPageUseCase
        )

        val stateUpdates = mutableListOf<CollectionState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateUpdates)
        }

        advanceUntilIdle()
        assertEquals(2, stateUpdates.size)
        val expectedFirstState = CollectionState(
            loading = true,
            data = PaginatedArtObjectViewData(
                isLoadingMore = false,
                canLoadMore = false,
                items = emptyList(),
                loadingMoreError = null,
            ),
            error = null
        )
        assertEquals(expectedFirstState, stateUpdates[0])
        val expectedSecondState = CollectionState(
            loading = false,
            data = PaginatedArtObjectViewData(
                isLoadingMore = false,
                canLoadMore = true,
                items = emptyList(),
                loadingMoreError = null,
            ),
            error = null
        )
        assertEquals(expectedSecondState, stateUpdates[1])
    }

    @Test
    fun `when loading consecutive pages then state is updated correctly`() = runTest {
        val firstPageResult = Result.success(
            PaginatedArtListModel(
                items = emptyList(),
                hasMoreItems = true,
                nextPageIndex = 2,
            )
        )
        val secondPageResult = Result.success(
            PaginatedArtListModel(
                items = emptyList(),
                hasMoreItems = false,
                nextPageIndex = 3,
            )
        )
        coEvery { getCollectionPageUseCase.get(eq(1)) } returns firstPageResult
        coEvery { getCollectionPageUseCase.get(eq(2)) } returns secondPageResult

        val viewModel = CollectionViewModel(
            getCollectionPage = getCollectionPageUseCase
        )

        val stateUpdates = mutableListOf<CollectionState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateUpdates)
        }

        advanceUntilIdle()
        viewModel.onMoreItemsRequested()
        advanceUntilIdle()
        println(stateUpdates)
        assertEquals(4, stateUpdates.size)
        val expectedInitialLoad = CollectionState(
            loading = true,
            data = PaginatedArtObjectViewData(
                isLoadingMore = false,
                canLoadMore = false,
                items = emptyList(),
                loadingMoreError = null,
            ),
            error = null
        )
        assertEquals(expectedInitialLoad, stateUpdates[0])
        val expectedShowingInitialPage = CollectionState(
            loading = false,
            data = PaginatedArtObjectViewData(
                isLoadingMore = false,
                canLoadMore = true,
                items = emptyList(),
                loadingMoreError = null,
            ),
            error = null
        )
        assertEquals(expectedShowingInitialPage, stateUpdates[1])
        val expectedLoadingNextPage = CollectionState(
            loading = false,
            data = PaginatedArtObjectViewData(
                isLoadingMore = true,
                canLoadMore = true,
                items = emptyList(),
                loadingMoreError = null,
            ),
            error = null
        )
        assertEquals(expectedLoadingNextPage, stateUpdates[2])
        val expectedShowingNextPage = CollectionState(
            loading = false,
            data = PaginatedArtObjectViewData(
                isLoadingMore = false,
                canLoadMore = false,
                items = emptyList(),
                loadingMoreError = null,
            ),
            error = null
        )
        assertEquals(expectedShowingNextPage, stateUpdates[3])
    }
}
