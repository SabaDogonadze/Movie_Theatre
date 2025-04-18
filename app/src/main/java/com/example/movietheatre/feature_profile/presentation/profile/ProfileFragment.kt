package com.example.movietheatre.feature_profile.presentation.profile

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private val pickImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.send(ProfileEvent.SelectImage(it)) } }

    override fun setUp() {
        collectLatestFlow(viewModel.state) { state ->
            state.currentImage?.let { uri ->
                Glide.with(this@ProfileFragment)
                    .load(uri)
                    .error(R.drawable.ic_profile)
                    .placeholder(R.drawable.ic_profile)
                    .into(binding.imgProfile)
            }
        }
        collectLatestFlow(viewModel.effect) { fx ->
            when (fx) {
                is ProfileEffect.ShowToast -> binding.root.showSnackBar(fx.message)
            }
        }
    }


    override fun clickListeners() {
        binding.ivSeeBookedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment())
        }
        binding.ivSeePurchasedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment2())
        }

        binding.imgProfile.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

}