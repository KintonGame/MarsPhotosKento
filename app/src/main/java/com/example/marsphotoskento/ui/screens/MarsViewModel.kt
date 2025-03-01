package com.example.marsphotoskento.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotoskento.network.MarsApi
import kotlinx.coroutines.launch

class MarsViewModel : ViewModel() {
    var marsUiState: String by mutableStateOf("")

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                marsUiState = listResult
            } catch (e: Exception) {
            }
        }
    }
}