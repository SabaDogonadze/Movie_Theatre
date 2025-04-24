package com.example.feature.login.presentation.screen.forgot_password

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.login.presentation.databinding.FragmentForgotPasswordBinding
import com.example.resource.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(
        FragmentForgotPasswordBinding::inflate
    ) {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun setUp() {
        collectFlows()
    }

    private fun collectFlows() {

        collectLatestFlow(viewModel.state) { state ->
            updateUI(state)
        }

        collectLatestFlow(viewModel.sideEffect) { effect ->
            handleSideEffect(effect)
        }
    }

    private fun updateUI(state: ForgotPasswordUiState) {
        binding.progressBar.isVisible = state.isLoading


        binding.txtResetEmailError.apply {
            isVisible = state.emailError != null
            text = state.emailError?.let { getString(it) }
        }



        binding.btnResetPassword.isEnabled = state.email.isNotEmpty() && state.emailError == null

    }

    private fun handleSideEffect(effect: ForgotPasswordSideEffect) {
        when (effect) {
            is ForgotPasswordSideEffect.ShowError -> {
                binding.root.showSnackBar(
                    message = getString(effect.message),
                    backgroundColor = R.color.red
                )
            }

            is ForgotPasswordSideEffect.ShowSuccess -> {
                binding.root.showSnackBar(
                    message = getString(effect.message),
                    backgroundColor = R.color.green,
                    textColor = R.color.white
                )
            }

            is ForgotPasswordSideEffect.NavigateBackToLogin -> {
                findNavController().navigateUp()
            }
        }
    }

    override fun clickListeners() {
        binding.etResetEmail.addTextChangedListener(afterTextChanged = { editable ->
            editable?.toString()?.let { email ->
                viewModel.onEvent(ForgotPasswordEvent.EmailChanged(email))
            }
        })

        binding.btnResetPassword.setOnClickListener {
            viewModel.onEvent(ForgotPasswordEvent.ResetPasswordClicked)
        }

        binding.txtBackToLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}