package com.example.movietheatre.feature_seat.presentation.screen

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.databinding.FragmentSeatBinding
import com.example.movietheatre.feature_seat.presentation.mapper.createRowModels
import com.example.movietheatre.feature_seat.presentation.screen.seat_row_adapter.SeatRowAdapter
import com.example.movietheatre.feature_seat.presentation.screen.seat_type_adapter.SeatTypeAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeatFragment : BaseFragment<FragmentSeatBinding>(FragmentSeatBinding::inflate) {

    private val viewModel: SeatViewModel by viewModels()

    private val seatRowAdapter: SeatRowAdapter by lazy {
        SeatRowAdapter(onSeatClicked = {
            viewModel.onEvent(SeatUiEvent.UpdateSeat(it))
        })
    }

    private val seatTypeAdapter: SeatTypeAdapter by lazy {
        SeatTypeAdapter()
    }


    override fun setUp() {
        viewModel.onEvent(SeatUiEvent.GetSeats)
        binding.rvSeatRow.apply {
            adapter = seatRowAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvSeatType.apply {
            adapter = seatTypeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        collectLatestFlow(viewModel.uiState) { updateUiState(it) }
    }

    override fun clickListeners() {
    }

    private fun updateUiState(uiState: SeatUiState) {
        Log.d("seatState", uiState.toString())
        binding.progressBar.root.isVisible = uiState.isLoading

        seatTypeAdapter.submitList(uiState.seatTypeInfo.toList())

        seatRowAdapter.submitList(uiState.seats.toList().createRowModels())
    }

}