package com.example.feature.profile.presentation.state

import com.google.firebase.auth.FirebaseUser

data class ProfileUiState(
    val isLoading: Boolean = false,
    val coin: Int = 0,
    val selectedCoin: Int = 0,
    val firebaseUser: FirebaseUser,
    val isConnected: Boolean = false,
)