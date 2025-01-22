package com.rijksmuseum.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rijksmuseum.domain.model.ArtDetailModel
import com.rijksmuseum.domain.usecase.GetArtDetailsUseCase
import com.rijksmuseum.presentation.mapper.toViewData
import com.rijksmuseum.presentation.model.ArtDetailViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject internal constructor(
    savedStateHandle: SavedStateHandle,
    private val getArtDetails: GetArtDetailsUseCase
) : ViewModel() {

    private val id: String = savedStateHandle.get<String>("id")
        ?: throw IllegalArgumentException("Required parameter id is not present")

    private val _uiState = MutableStateFlow<ArtDetailsState>(ArtDetailsState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadDetails()
    }

    fun onRetryClicked() {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            _uiState.update { ArtDetailsState.Loading }
            getArtDetails.get(id)
                .fold(
                    onSuccess = ::requestDetailsSuccess,
                    onFailure = ::requestDetailsFailure,
                )
        }
    }

    private fun requestDetailsSuccess(data: ArtDetailModel) {
        _uiState.update {
            ArtDetailsState.Loaded(
                data = data.toViewData()
            )
        }
    }

    private fun requestDetailsFailure(error: Throwable) {
        _uiState.update { ArtDetailsState.Error(reason = error.message.orEmpty()) }
    }
}

sealed class ArtDetailsState {

    data object Loading : ArtDetailsState()

    data class Error(
        val reason: String
    ) : ArtDetailsState()

    data class Loaded(
        val data: ArtDetailViewData
    ) : ArtDetailsState()
}
