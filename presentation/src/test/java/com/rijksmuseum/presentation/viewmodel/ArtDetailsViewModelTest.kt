@file:OptIn(ExperimentalCoroutinesApi::class)

package com.rijksmuseum.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.domain.usecase.GetArtDetailsUseCase
import com.rijksmuseum.presentation.model.ArtDetailViewData
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

class ArtDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getArtDetailsUseCase = mockk<GetArtDetailsUseCase>(relaxed = true)

    @Test
    fun `when initialised then details are fetched`() = runTest {
        val initialDetailModel = ArtDetailModel(
            id = "adipisci",
            objectNumber = "graeco",
            title = "ornare",
            maker = "repudiare",
            image = null,
            location = null,
            dating = null,
            description = null
        )
        val result = Result.success(initialDetailModel)
        coEvery { getArtDetailsUseCase.get(eq("defaultId")) } returns result
        ArtDetailsViewModel(
            savedStateHandle = SavedStateHandle(mapOf("id" to "defaultId")),
            getArtDetails = getArtDetailsUseCase,
        )

        advanceUntilIdle()

        coVerify { getArtDetailsUseCase.get(eq("defaultId")) }
    }

    @Test
    fun `when details are fetched with success then state is updated correctly`() = runTest {
        val initialDetailModel = ArtDetailModel(
            id = "adipisci",
            objectNumber = "graeco",
            title = "ornare",
            maker = "repudiare",
            image = null,
            location = null,
            dating = null,
            description = null
        )
        coEvery { getArtDetailsUseCase.get(eq("defaultId")) } returns Result.success(initialDetailModel)
        val viewModel = ArtDetailsViewModel(
            savedStateHandle = SavedStateHandle(mapOf("id" to "defaultId")),
            getArtDetails = getArtDetailsUseCase,
        )

        val stateUpdates = mutableListOf<ArtDetailsState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateUpdates)
        }

        advanceUntilIdle()
        assertEquals(2, stateUpdates.size)
        assertEquals(ArtDetailsState.Loading, stateUpdates[0])
        val expectedLoadedState = ArtDetailsState.Loaded(
            data = ArtDetailViewData(
                objectNumber = "graeco",
                title = "ornare",
                maker = "repudiare",
                image = null,
                location = null,
                dating = null,
                description = null
            )
        )
        assertEquals(expectedLoadedState, stateUpdates[1])
    }

    @Test
    fun `when details are fetched with failure then state is updated correctly`() = runTest {
        val exception = Exception("No network error")
        coEvery { getArtDetailsUseCase.get(eq("defaultId")) } returns Result.failure(exception)
        val viewModel = ArtDetailsViewModel(
            savedStateHandle = SavedStateHandle(mapOf("id" to "defaultId")),
            getArtDetails = getArtDetailsUseCase,
        )

        val stateUpdates = mutableListOf<ArtDetailsState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(stateUpdates)
        }

        advanceUntilIdle()
        assertEquals(2, stateUpdates.size)
        assertEquals(ArtDetailsState.Loading, stateUpdates[0])
        val expectedErrorState = ArtDetailsState.Error(
            reason = "No network error"
        )
        assertEquals(expectedErrorState, stateUpdates[1])
    }

    @Test
    fun `when retry is clicked then data is fetched`() = runTest {
        val result = Result.failure<ArtDetailModel>(Exception("No internet"))
        coEvery { getArtDetailsUseCase.get(eq("defaultId")) } returns result
        val viewModel = ArtDetailsViewModel(
            savedStateHandle = SavedStateHandle(mapOf("id" to "defaultId")),
            getArtDetails = getArtDetailsUseCase,
        )

        advanceUntilIdle()
        viewModel.onRetryClicked()
        advanceUntilIdle()
        coVerify(exactly = 2) { getArtDetailsUseCase.get(eq("defaultId")) }
    }
}
