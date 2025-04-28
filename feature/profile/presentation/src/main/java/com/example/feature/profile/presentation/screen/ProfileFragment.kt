package com.example.feature.profile.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.profile.presentation.databinding.FragmentProfileBinding
import com.example.feature.profile.presentation.event.ProfileEvent
import com.example.feature.profile.presentation.event.ProfileSideEffect
import com.example.feature.profile.presentation.state.ProfileUiState
import com.example.navigation.NavigationCommands
import com.example.core.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewmodel: ProfileViewModel by viewModels()

    override fun setUp() {
        collectLatestFlow(viewmodel.uiState) {
            updateUi(it)
        }

        collectLatestFlow(viewmodel.sideEffects) {
            getSideEffects(it)
        }
    }

    private fun updateUi(uiState: ProfileUiState) {
        binding.progressBar.root.isVisible = uiState.isLoading

        binding.totalCoins.txtCoinCount.text = uiState.coin.toString()

        binding.tvUserEmail.text = uiState.firebaseUser.email
    }

    private fun getSideEffects(sideEffect: ProfileSideEffect) {
        when (sideEffect) {
            is ProfileSideEffect.ShowError -> {
                when (sideEffect.message) {
                    R.string.connection_problem -> {
                        binding.btnLoginOut.isVisible = false
                        binding.totalCoins.root.isVisible = false
                    }

                    else ->
                        binding.root.showSnackBar(
                            getString(sideEffect.message),
                            backgroundColor = R.color.red
                        )
                }
            }

            ProfileSideEffect.SignOut -> NavigationCommands.navigateToLoginGraph(findNavController())
        }
    }

    override fun clickListeners() {
        binding.ivSeeBookedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketHeldFragment())
        }
        binding.ivSeePurchasedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment())
        }
        binding.ivMyShop.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToMyShopFragment())
        }
        binding.btnLoginOut.setOnClickListener {
            viewmodel.onEvent(ProfileEvent.SignOut)
        }
    }

}