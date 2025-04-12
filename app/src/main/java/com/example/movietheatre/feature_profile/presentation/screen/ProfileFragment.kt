package com.example.movietheatre.feature_profile.presentation.screen

import androidx.navigation.fragment.findNavController
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.databinding.FragmentProfileBinding
import java.util.jar.Pack200.Packer.PASS

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun setUp() {
        PASS
    }

    override fun clickListeners() {
       binding.ivSeeBookedTickets.setOnClickListener {
           findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment())
       }
        binding.ivSeePurchasedTickets.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionIdProfileFragmentToTicketBookedFragment2())
        }
    }

}