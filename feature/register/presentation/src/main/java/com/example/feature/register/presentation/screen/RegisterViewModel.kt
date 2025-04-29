package com.example.feature.register.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.feature.register.domain.use_case.RegisterUseCaseWrapper
import com.example.feature.register.presentation.extension.asStringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCaseWrapper: RegisterUseCaseWrapper,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _sideEffects = MutableSharedFlow<RegisterSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(
                event.email,
                event.password
            )

            is RegisterEvent.ValidateEmail -> validateEmail(event.email)
            is RegisterEvent.ValidatePassword -> validatePassword(event.password)
            is RegisterEvent.ValidateRepeatedPassword -> validateRepeatPassword(
                event.password,
                event.repeatedPassword
            )
        }
    }

    private fun register(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = registerUseCaseWrapper.registerUseCase(email, password)) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _sideEffects.emit(RegisterSideEffect.ShowSnackBar(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _sideEffects.emit(RegisterSideEffect.NavigateToLoginScreen)
                }
            }

        }
    }

    private fun validateEmail(email: String) {
        when (val result = registerUseCaseWrapper.validateEmailUseCase(email)) {
            is Resource.Error -> _uiState.update { it.copy(emailError = result.error.asStringResource()) }
            is Resource.Success -> _uiState.update {
                it.copy(
                    emailError = null, isEmailValid = true, isValidForm =
                    _uiState.value.isPasswordValid
                            && _uiState.value.isRepeatedPasswordValid
                )
            }
        }
    }

    private fun validatePassword(password: String) {
        when (val result = registerUseCaseWrapper.validatePasswordUseCase(password)) {
            is Resource.Error -> _uiState.update {
                it.copy(
                    passwordError = result.error.asStringResource()
                )
            }

            is Resource.Success -> _uiState.update {
                it.copy(
                    passwordError = null, isPasswordValid = true, isValidForm =
                    _uiState.value.isEmailValid
                            && _uiState.value.isRepeatedPasswordValid
                )
            }
        }
    }

    private fun validateRepeatPassword(password: String, repeatPassword: String) {
        val result =
            registerUseCaseWrapper.validateRepeatPasswordUseCase(
                password = password,
                repeatedPassword = repeatPassword
            )
        when (result) {
            is Resource.Error -> _uiState.update {
                it.copy(
                    repeatedPasswordError = result.error.asStringResource()
                )
            }

            is Resource.Success -> _uiState.update {
                it.copy(
                    repeatedPasswordError = null, isRepeatedPasswordValid = true, isValidForm =
                    _uiState.value.isEmailValid
                            && _uiState.value.isPasswordValid
                )
            }
        }
    }
}