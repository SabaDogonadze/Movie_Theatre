package com.example.movietheatre.feature_seat.presentation.screen

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.databinding.FragmentSeatBinding
import com.example.movietheatre.feature_seat.presentation.extension.asString
import com.example.movietheatre.feature_seat.presentation.mapper.createRowModels
import com.example.movietheatre.feature_seat.presentation.screen.seat_row_adapter.SeatRowAdapter
import com.example.movietheatre.feature_seat.presentation.screen.seat_type_adapter.SeatTypeAdapter
import com.example.movietheatre.feature_seat.presentation.util.SeatType
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeatFragment : BaseFragment<FragmentSeatBinding>(FragmentSeatBinding::inflate) {
    private val args: SeatFragmentArgs by navArgs()
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
        viewModel.onEvent(SeatUiEvent.GetSeats(args.screeningId))
        binding.rvSeatRow.apply {
            adapter = seatRowAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvSeatType.apply {
            adapter = seatTypeAdapter
            layoutManager = FlexboxLayoutManager(binding.root.context).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.SPACE_BETWEEN
            }
        }

        binding.txtTicketPriceValue.text = args.TicketPrice.asMoneyFormat()

        collectLatestFlow(viewModel.uiState) { updateUiState(it) }
        collectLatestFlow(viewModel.sideEffects) { getSideEffects(it) }
    }

    override fun clickListeners() {
        binding.btnBookTickets.setOnClickListener {
            viewModel.onEvent(SeatUiEvent.UpdateTicker(args.screeningId, TicketStatus.HELD))
        }
    }

    private fun updateUiState(uiState: SeatUiState) {
        Log.d("seatState", uiState.toString())
        binding.progressBar.root.isVisible = uiState.isLoading

        seatTypeAdapter.submitList(uiState.seatTypeInfo.toList())

        seatRowAdapter.submitList(uiState.seats.toList().createRowModels())

        val selectedSeats = uiState.seats.filter { it.status == SeatType.SELECTED }

        binding.txtSeatsValue.text = selectedSeats.joinToString(", ") { it.asString() }

        binding.txtVipAddOnValue.text =
            selectedSeats.sumOf { it.vipAddOn }.toFloat().asMoneyFormat()

        binding.txtTotalPriceValue.text = selectedSeats.sumOf { seat ->
            seat.vipAddOn + args.TicketPrice
        }.toFloat().asMoneyFormat()
    }

    private fun getSideEffects(sideEffect: SeatSideEffect) {
        when (sideEffect) {
            SeatSideEffect.NavigateToDetailScreen -> findNavController().navigateUp()
            is SeatSideEffect.ShowError -> binding.root.showSnackBar(getString(sideEffect.message))
            SeatSideEffect.ShowSuccessfulHoldScreen -> binding.SuccessBookingLayout.root.isVisible =
                true
        }
    }

}