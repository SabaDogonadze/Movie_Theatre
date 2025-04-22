package com.example.movietheatre.feature_profile.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentProfileBinding
import com.example.movietheatre.feature_profile.presentation.event.ProfileSideEffect
import com.example.movietheatre.feature_profile.presentation.state.ProfileUiState
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

        binding.txtCoin.text = uiState.coin.toString()
    }

    private fun getSideEffects(sideEffect: ProfileSideEffect) {
        when (sideEffect) {
            is ProfileSideEffect.ShowError -> binding.root.showSnackBar(
                getString(sideEffect.message),
                backgroundColor = R.color.red
            )
        }
    }

    override fun clickListeners() {
        binding.ivSeeBookedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment())
        }
        binding.ivSeePurchasedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment2())
        }
        binding.ivMyShop.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToMyShopFragment())
        }
    }

}