package com.example.movietheatre.feature_seat.presentation.screen

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentSeatBinding
import com.example.movietheatre.feature_seat.presentation.extension.asString
import com.example.movietheatre.feature_seat.presentation.extension.roundToTwoDecimalPlaces
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

    private val seatRowAdapter by lazy {
        SeatRowAdapter { seat -> viewModel.onEvent(SeatUiEvent.UpdateSeat(seat)) }
    }
    private val seatTypeAdapter by lazy { SeatTypeAdapter() }

    override fun setUp() {
        viewModel.onEvent(SeatUiEvent.GetSeats(args.screeningId))

        binding.apply {
            rvSeatRow.apply {
                adapter = seatRowAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            rvSeatType.apply {
                adapter = seatTypeAdapter
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.SPACE_BETWEEN
                }
            }
            txtTicketPriceValue.text = args.ticketPrice.asMoneyFormat()
        }

        collectLatestFlow(viewModel.uiState) { updateUiState(it) }
        collectLatestFlow(viewModel.sideEffects) { handleSideEffects(it) }
    }

    override fun clickListeners() {
        binding.apply {
            btnBookTickets.setOnClickListener {
                viewModel.onEvent(SeatUiEvent.BookTicket(args.screeningId))
            }
            btnBuyOption.setOnClickListener {
                viewModel.onEvent(
                    SeatUiEvent.BuyTicket(
                        args.screeningId,
                        args.ticketPrice.toDouble()
                    )
                )
            }
        }
    }

    private fun updateUiState(uiState: SeatUiState) {
        binding.progressBar.root.isVisible = uiState.isLoading

        seatTypeAdapter.submitList(uiState.seatTypeInfo.toList())
        seatRowAdapter.submitList(uiState.seats.toList().createRowModels())

        val selectedSeats = uiState.seats.filter { it.status == SeatType.SELECTED }
        binding.apply {
            txtSeatsValue.text = selectedSeats.joinToString(", ") { it.asString() }
            txtVipAddOnValue.text = selectedSeats.sumOf { it.vipAddOn }.toFloat().asMoneyFormat()
            txtTotalPriceValue.text =
                selectedSeats.sumOf { it.vipAddOn + args.ticketPrice }.roundToTwoDecimalPlaces()
                    .asMoneyFormat()
        }
    }

    private fun handleSideEffects(effect: SeatSideEffect) {
        when (effect) {
            SeatSideEffect.NavigateToDetailScreen -> findNavController().navigateUp()
            is SeatSideEffect.ShowError -> binding.root.showSnackBar(
                getString(effect.message),
                backgroundColor = R.color.red
            )

            SeatSideEffect.ShowSuccessfulHoldScreen -> {
                binding.apply {
                    btnBuyOption.isVisible = false
                    btnBookTickets.isVisible = false

                    binding.SuccessBookingLayout.imgPause.playAnimation()
                    val transition = AutoTransition().apply {
                        duration = 300
                    }
                    TransitionManager.beginDelayedTransition(binding.root, transition)
                    SuccessBookingLayout.root.visibility = View.VISIBLE
                }
            }

            is SeatSideEffect.NavigateToPaymentScreen -> {
                findNavController().navigate(
                    SeatFragmentDirections.actionSeatFragmentToPaymentFragment(
                        args.screeningId,
                        seats = effect.seats.toTypedArray(),
                        totalPrice = effect.totalPrice.toFloat()
                    )
                )
            }
        }
    }
}