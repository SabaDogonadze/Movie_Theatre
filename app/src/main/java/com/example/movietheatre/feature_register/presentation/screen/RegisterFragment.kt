package com.example.movietheatre.feature_register.presentation.screen

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.core.presentation.extension.collectLastState
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentRegisterBinding
import com.example.movietheatre.feature_register.presentation.extension.asStringResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun setUp() {
        collectLastState(viewModel.uiState) { state ->
            updateUiState(state)
        }

        collectLastState(viewModel.uiEvents) { event ->
            getEvents(event)
        }
    }

    override fun clickListeners() {
        binding.apply {
            etEmail.doAfterTextChanged {
                viewModel.onEvent(RegisterEvent.ValidateEmail(it.toString()))
            }
            etPassword.doAfterTextChanged {
                viewModel.onEvent(RegisterEvent.ValidatePassword(it.toString()))
            }
            etRepeatPassword.doAfterTextChanged {
                viewModel.onEvent(

                    RegisterEvent.ValidateRepeatedPassword(
                        binding.etPassword.text.toString(),
                        it.toString()
                    )
                )
            }

            btnRegister.setOnClickListener {
                register()
            }

            txtAlreadyHaveAccount.setOnClickListener { findNavController().navigateUp() }
            btnArrowBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun register() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.onEvent(
            RegisterEvent.Register(
                email = email,
                password = password
            )
        )
    }

    private fun updateUiState(state: RegisterUiState) {
        showLoadingScreen(state.isLoading)


        binding.txtEmailError.apply {
            text = state.emailError?.let { getString(it.asStringResource()) }
            isVisible = state.emailError != null
        }

        binding.txtPasswordError.apply {
            text = state.passwordError?.let { getString(it.asStringResource()) }
            isVisible = state.passwordError != null
        }
        binding.txtRepeatPasswordError.apply {
            text = state.repeatedPasswordError?.let { getString(it.asStringResource()) }
            isVisible = state.repeatedPasswordError != null

        }

        binding.btnRegister.apply {
            isEnabled = state.isValidForm
        }
    }

    private fun getEvents(event: RegisterSideEffect) {
        when (event) {
            RegisterSideEffect.NavigateToLoginScreen -> {
                findNavController().popBackStack()
            }

            is RegisterSideEffect.ShowSnackBar -> {
                binding.root.showSnackBar(getString(event.message.asStringResource()))
            }
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.apply {
            progressBar.root.isVisible = isLoading
        }
    }

}