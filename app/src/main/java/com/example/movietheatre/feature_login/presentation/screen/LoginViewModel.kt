package com.example.movietheatre.feature_login.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.preference_key.PreferenceKeys.REMEMBER_ME
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_login.domain.use_case.LoginUseCaseWrapper
import com.example.movietheatre.feature_login.presentation.extension.asStringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCaseWrapper: LoginUseCaseWrapper,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _uiEventChannel = Channel<LoginSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password, rememberMe = event.rememberMe)
            is LoginEvent.ValidateEmail -> validateEmail(event.email)
            is LoginEvent.ValidatePassword -> validatePassword(event.password)
        }
    }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = loginUseCaseWrapper.loginUseCase(email, password)) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEventChannel.send(LoginSideEffect.ShowSnackBar(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    withContext(NonCancellable) {
                        loginUseCaseWrapper.saveValueToLocalStorageUseCase(REMEMBER_ME, rememberMe)
                    }
                    _uiEventChannel.send(LoginSideEffect.SuccessFullLogin)
                }
            }
        }
    }

    private fun validateEmail(email: String) {
        when (val result = loginUseCaseWrapper.validateEmailUseCase(email)) {
            is Resource.Error -> _uiState.update {
                it.copy(
                    emailError = result.error,
                    isEmailValid = false,
                    isValidForm = false
                )
            }

            is Resource.Success -> _uiState.update { currentState ->
                currentState.copy(
                    emailError = null,
                    isEmailValid = true,
                    isValidForm = currentState.isPasswordValid
                )
            }
        }
    }

    private fun validatePassword(password: String) {
        when (val result = loginUseCaseWrapper.validatePasswordUseCase(password)) {
            is Resource.Error -> _uiState.update {
                it.copy(
                    passwordError = result.error,
                    isPasswordValid = false,
                    isValidForm = false
                )
            }

            is Resource.Success -> _uiState.update { currentState ->
                currentState.copy(
                    passwordError = null,
                    isPasswordValid = true,
                    isValidForm = currentState.isEmailValid
                )
            }
        }
    }
}