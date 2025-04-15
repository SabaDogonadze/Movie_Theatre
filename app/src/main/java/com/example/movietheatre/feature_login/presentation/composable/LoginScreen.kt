package com.example.movietheatre.feature_login.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.component.FormField
import com.example.movietheatre.core.presentation.component.HeaderText
import com.example.movietheatre.core.presentation.component.LoadingOverlay
import com.example.movietheatre.core.presentation.component.PasswordField
import com.example.movietheatre.core.presentation.component.PrimaryButton
import com.example.movietheatre.core.presentation.extension.CollectAsUiEvents
import com.example.movietheatre.core.presentation.extension.hideKeyboard
import com.example.movietheatre.feature_login.presentation.screen.login.LoginEvent
import com.example.movietheatre.feature_login.presentation.screen.login.LoginSideEffect
import com.example.movietheatre.feature_login.presentation.screen.login.LoginUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    uiEvents: Flow<LoginSideEffect>,
    snackBarHostState: SnackbarHostState,
) {

    val context = LocalContext.current
    val hideKeyboardAction = hideKeyboard()

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            is LoginSideEffect.ShowSnackBar -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(event.message),
                    duration = SnackbarDuration.Short

                )
            }

            LoginSideEffect.SuccessFullLogin -> {
                navigateToHomeScreen()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState, snackbar = {
                Snackbar(
                    snackbarData = it,
                    containerColor = colorResource(R.color.red),
                    contentColor = colorResource(R.color.white)
                )
            })
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.blue))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderText(text = stringResource(id = R.string.login))

                    Spacer(modifier = Modifier.height(24.dp))

                    FormField(
                        value = uiState.email,
                        onValueChange = { onEvent(LoginEvent.ValidateEmail(it)) },
                        labelResId = R.string.enter_email,
                        icon = Icons.Default.Email,
                        isError = uiState.emailError != null,
                        errorMessage = uiState.emailError?.let { stringResource(id = it) },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    )

                    Spacer(modifier = Modifier.height(12.dp))


                    PasswordField(
                        value = uiState.password,
                        onValueChange = { onEvent(LoginEvent.ValidatePassword(it)) },
                        labelResId = R.string.enter_password,
                        isVisible = uiState.passwordIsVisible,
                        onVisibilityChange = { onEvent(LoginEvent.PasswordVisibilityChanged) },
                        isError = uiState.passwordError != null,
                        errorMessage = uiState.passwordError?.let { stringResource(id = it) },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(id = R.string.remember_me),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Checkbox(
                                checked = uiState.rememberMeChecked,
                                onCheckedChange = { onEvent(LoginEvent.RememberMeChanged) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.White,
                                    uncheckedColor = Color.Gray,
                                    checkmarkColor = colorResource(id = R.color.blue)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(onClick = onForgotPasswordClick) {
                            Text(
                                text = "Forgot Password?",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    PrimaryButton(
                        textResId = R.string.login,
                        onClick = {
                            hideKeyboardAction()
                            onEvent(LoginEvent.Login)
                        },
                        enabled = uiState.isValidForm
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Register text
                    TextButton(onClick = onRegisterClick) {
                        Text(
                            text = stringResource(id = R.string.don_t_have_account_sign_up),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }

                if (uiState.isLoading) {
                    LoadingOverlay()
                }

            }
        })
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(
            isValidForm = true,
            email = "3432",
            rememberMeChecked = true,
            password = "skf",
            passwordIsVisible = true
        ),
        onEvent = {},
        uiEvents = flowOf(),
        onForgotPasswordClick = {},
        onRegisterClick = {}, navigateToHomeScreen = {}, snackBarHostState = SnackbarHostState()
    )
}