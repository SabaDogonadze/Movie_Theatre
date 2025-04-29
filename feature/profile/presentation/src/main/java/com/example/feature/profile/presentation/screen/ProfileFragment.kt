package com.example.feature.profile.presentation.screen

import android.content.res.ColorStateList
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.profile.domain.model.Language
import com.example.feature.profile.presentation.databinding.FragmentProfileBinding
import com.example.feature.profile.presentation.event.ProfileEvent
import com.example.feature.profile.presentation.event.ProfileSideEffect
import com.example.feature.profile.presentation.state.ProfileUiState
import com.example.navigation.NavigationCommands
import com.google.android.material.chip.Chip
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

        setupLanguageChips(uiState.availableLanguages, uiState.selectedLanguageCode)

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
            is ProfileSideEffect.ApplyLanguage -> {
                val newLocales = LocaleListCompat.forLanguageTags(sideEffect.languageCode)
                AppCompatDelegate.setApplicationLocales(newLocales)
            }
        }
    }

    private fun setupLanguageChips(languages: List<Language>, selectedCode: String) {
        binding.languageChipGroup.removeAllViews()

        languages.forEach { language ->
            val chip = Chip(requireContext()).apply {
                id = View.generateViewId()
                text = language.displayName
                isCheckable = true
                isChecked = language.code == selectedCode

                chipBackgroundColor = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf(-android.R.attr.state_checked)
                    ),
                    intArrayOf(
                        ContextCompat.getColor(requireContext(), R.color.white),
                        ContextCompat.getColor(requireContext(), R.color.blue)
                    )
                )

                setTextColor(
                    ColorStateList(
                        arrayOf(
                            intArrayOf(android.R.attr.state_checked),
                            intArrayOf(-android.R.attr.state_checked)
                        ),
                        intArrayOf(
                            ContextCompat.getColor(requireContext(), R.color.blue),
                            ContextCompat.getColor(requireContext(), R.color.white)
                        )
                    )
                )

                isChipIconVisible = language.code == selectedCode
                chipIcon = if (language.code == selectedCode) {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
                } else null

                setOnClickListener {
                    viewmodel.onEvent(ProfileEvent.SelectLanguage(language.code))
                }
            }

            binding.languageChipGroup.addView(chip)
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