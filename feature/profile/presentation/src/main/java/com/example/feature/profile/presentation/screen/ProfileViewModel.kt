package com.example.feature.profile.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.GetCoinsUseCase
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.feature.profile.domain.use_case.SignOutUseCase
import com.example.feature.profile.presentation.event.ProfileEvent
import com.example.feature.profile.presentation.event.ProfileSideEffect
import com.example.feature.profile.presentation.mapper.asStringResource
import com.example.feature.profile.presentation.state.ProfileUiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(ProfileUiState(firebaseUser = firebaseAuth.currentUser!!))
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _sideEffects = MutableSharedFlow<ProfileSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        onEvent(ProfileEvent.GetCoins)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.GetCoins -> getCoins()
            ProfileEvent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = signOutUseCase()) {
                is Resource.Error -> {
                    _sideEffects.emit(ProfileSideEffect.ShowError(result.error.asStringResource()))
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Resource.Success -> {
                    _sideEffects.emit(ProfileSideEffect.SignOut)
                }
            }
        }
    }

    private fun getCoins() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getCoinsUseCase()) {
                is Resource.Error -> {
                    _sideEffects.emit(ProfileSideEffect.ShowError(result.error.asStringResource()))
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            coin = result.data.coins
                        )
                    }
                }
            }
        }
    }
}