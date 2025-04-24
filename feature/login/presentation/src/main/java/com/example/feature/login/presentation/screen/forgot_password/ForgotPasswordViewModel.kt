package com.example.feature.login.presentation.screen.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.ValidateEmailUseCase
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.feaute.login.domain.use_case.ForgotPasswordUseCase
import com.example.feaute.login.domain.util.ForgotPasswordError
import com.example.resource.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordUiState())
    val state: StateFlow<ForgotPasswordUiState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ForgotPasswordSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        emailError = when (val result = validateEmailUseCase(event.email)) {
                            is Resource.Error -> result.error.asStringResource()
                            is Resource.Success -> null
                        }
                    )

                }
            }

            is ForgotPasswordEvent.ResetPasswordClicked -> {
                resetPassword()
            }


        }
    }

    private fun resetPassword() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = forgotPasswordUseCase(state.value.email)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _sideEffect.emit(ForgotPasswordSideEffect.ShowSuccess(R.string.password_reset_link_sent_to_your_email))
                    _sideEffect.emit(ForgotPasswordSideEffect.NavigateBackToLogin)
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            forgotPasswordError = result.error
                        )
                    }

                    val errorMessage = when (result.error) {
                        ForgotPasswordError.NETWORK_ERROR -> R.string.network_error_please_check_your_connection
                        ForgotPasswordError.TOO_MANY_REQUEST -> R.string.too_many_requests_please_try_again_later
                        ForgotPasswordError.USER_NOT_FOUND -> R.string.no_account_found_with_this_email
                        ForgotPasswordError.UNKNOWN_ERROR -> R.string.unknown_error
                    }
                    _sideEffect.emit(ForgotPasswordSideEffect.ShowError(errorMessage))
                }
            }
        }
    }
}