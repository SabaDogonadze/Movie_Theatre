package com.example.movietheatre.feature_login.presentation.screen.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.hideKeyboard
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentLoginBinding
import com.example.movietheatre.feature_login.presentation.composable.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

//@AndroidEntryPoint
//class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
//    private val viewModel: LoginViewModel by viewModels()
//
//    override fun setUp() {
//        collectLatestFlow(viewModel.uiState) { state ->
//            updateUi(state)
//        }
//        collectLatestFlow(viewModel.sideEffects) { event ->
//            getSideEffects(event)
//        }
//    }
//
//    override fun clickListeners() {
//        binding.apply {
//            etEmail.doAfterTextChanged { text ->
//                if (etEmail.hasFocus()) {
//                    viewModel.onEvent(LoginEvent.ValidateEmail(text.toString()))
//                }
//            }
//
//            etPassword.doAfterTextChanged { text ->
//                if (etPassword.hasFocus()) {
//                    viewModel.onEvent(LoginEvent.ValidatePassword(text.toString()))
//                }
//            }
//
//            btnLogin.setOnClickListener {
//                login()
//            }
//
//            txtRegister.setOnClickListener {
//                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
//            }
//
//            txtForgotPassword.setOnClickListener {
//                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
//            }
//        }
//    }
//
//    private fun updateUi(state: LoginUiState) {
//        showLoadingScreen(state.isLoading)
//
//        binding.txtEmailError.apply {
//            text = state.emailError?.let { getString(it) }
//            isVisible = state.emailError != null
//        }
//
//        binding.txtPasswordError.apply {
//            text = state.passwordError?.let { getString(it) }
//            isVisible = state.passwordError != null
//        }
//
//        binding.btnLogin.apply {
//            isEnabled = state.isValidForm
//
//        }
//    }
//
//    private fun getSideEffects(event: LoginSideEffect) {
//        when (event) {
//            is LoginSideEffect.SuccessFullLogin -> onSuccessFullLogin()
//
//            is LoginSideEffect.ShowSnackBar -> binding.root.showSnackBar(
//                getString(event.message),
//                backgroundColor = R.color.red
//            )
//        }
//    }
//
//    private fun login() {
//        binding.root.hideKeyboard()
//        val email = binding.etEmail.text.toString()
//        val password = binding.etPassword.text.toString()
//        viewModel.onEvent(
//            LoginEvent.Login(
//                email = email,
//                password = password,
//                rememberMe = binding.cbRememberMe.isChecked
//            )
//        )
//    }
//
//
//    private fun onSuccessFullLogin() {
//        findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
//    }
//
//    private fun showLoadingScreen(isLoading: Boolean) {
//        binding.apply {
//            progressBar.root.isVisible = isLoading
//        }
//    }
//}