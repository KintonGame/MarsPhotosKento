package com.example.marsphotoskento.ui.screens

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Loading : MarsUiState
    object Error : MarsUiState
}