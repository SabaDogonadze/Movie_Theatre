package com.example.feature.profile.presentation.state

data class ProfileUiState(
    val isLoading: Boolean = false,
    val coin: Int = 0,
    val selectedCoin: Int = 0,
)