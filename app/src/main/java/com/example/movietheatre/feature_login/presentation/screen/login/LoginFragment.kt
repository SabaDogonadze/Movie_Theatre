package com.example.movietheatre.feature_login.presentation.screen.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.R
import com.example.movietheatre.feature_login.presentation.composable.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val snackBarHostState = remember { SnackbarHostState() }

                LoginScreen(
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    onRegisterClick = {
                        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                    },
                    onForgotPasswordClick = {
                        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
                    },
                    navigateToHomeScreen = {
                        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
                    },
                    uiEvents = viewModel.sideEffects,
                    snackBarHostState = snackBarHostState
                )

            }
        }
    }
}
