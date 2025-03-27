package com.example.movietheatre.feature_register.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_register.domain.use_case.RegisterUseCaseWrapper
import com.example.movietheatre.feature_register.presentation.extension.asStringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiEventChannel = Channel<RegisterSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


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
                    _uiEventChannel.send(RegisterSideEffect.ShowSnackBar(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _uiEventChannel.send(RegisterSideEffect.NavigateToLoginScreen)
                }
            }

        }
    }

    private fun validateEmail(email: String) {
        when (val result = registerUseCaseWrapper.validateEmailUseCase(email)) {
            is Resource.Error -> _uiState.update { it.copy(emailError = result.error) }
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
            is Resource.Error -> _uiState.update { it.copy(passwordError = result.error) }
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
            is Resource.Error -> _uiState.update { it.copy(repeatedPasswordError = result.error) }
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