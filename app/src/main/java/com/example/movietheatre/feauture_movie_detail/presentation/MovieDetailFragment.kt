package com.example.movietheatre.feauture_movie_detail.presentation

import androidx.navigation.fragment.findNavController
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    override fun setUp() {

    }

    override fun clickListeners() {
        binding.btnGoToSeatScreen.setOnClickListener {
            findNavController().navigate(
                MovieDetailFragmentDirections.actionMovieDetailFragmentToSeatFragment(
                    screeningId = 3,
                    ticketPrice = 14.99f
                )
            )
        }
    }

}