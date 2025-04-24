package com.example.feature.profile.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.profile.presentation.databinding.FragmentProfileBinding
import com.example.feature.profile.presentation.event.ProfileSideEffect
import com.example.feature.profile.presentation.state.ProfileUiState
import com.example.resource.R
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