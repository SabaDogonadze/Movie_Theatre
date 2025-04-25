package com.example.feature.register.presentation.screen

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.hideKeyboard
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.register.presentation.databinding.FragmentRegisterBinding
import com.example.resource.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun setUp() {
        collectLatestFlow(viewModel.uiState) { state ->
            updateUiState(state)
        }

        collectLatestFlow(viewModel.sideEffects) { event ->
            getSideEffects(event)
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
        binding.root.hideKeyboard()
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
            text = state.emailError?.let { getString(it) }
            isVisible = state.emailError != null
        }

        binding.txtPasswordError.apply {
            text = state.passwordError?.let { getString(it) }
            isVisible = state.passwordError != null
        }
        binding.txtRepeatPasswordError.apply {
            text = state.repeatedPasswordError?.let { getString(it) }
            isVisible = state.repeatedPasswordError != null

        }

        binding.btnRegister.apply {
            isEnabled = state.isValidForm
        }
    }

    private fun getSideEffects(event: RegisterSideEffect) {
        when (event) {
            RegisterSideEffect.NavigateToLoginScreen -> {
                findNavController().popBackStack()
            }

            is RegisterSideEffect.ShowSnackBar -> {
                binding.root.showSnackBar(getString(event.message), backgroundColor = R.color.red)
            }
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.apply {
            progressBar.root.isVisible = isLoading
        }
    }

}