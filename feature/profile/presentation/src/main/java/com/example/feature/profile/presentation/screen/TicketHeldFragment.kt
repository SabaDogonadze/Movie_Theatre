package com.example.feature.profile.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.SwipeAndDeleteCallback
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.profile.presentation.databinding.FragmentTicketHeldBinding
import com.example.feature.profile.presentation.event.TicketHeldEvent
import com.example.feature.profile.presentation.event.TicketHeldSideEffect
import com.example.navigation.NavigationCommands
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketHeldFragment :
    BaseFragment<FragmentTicketHeldBinding>(FragmentTicketHeldBinding::inflate) {
    private val ticketHeldViewModel: TicketHeldViewModel by viewModels()
    private lateinit var tickedHeldAdapter: TicketHeldRecyclerView

    override fun setUp() {
        stateObserver()
        eventObserver()
        setUpActorsRecycler()
        swipeAndDelete()
    }

    override fun clickListeners() {
        tickedHeldAdapter.setonItemClickedListener {
            ticketHeldViewModel.event(
                TicketHeldEvent.TicketItemClicked(
                    screeningId = it.screeningId,
                    totalPrice = it.totalMoney.toFloat(),
                    seatNumbers = it.seatNumbers.split(",")
                )
            )
        }
    }


    private fun setUpActorsRecycler() {
        tickedHeldAdapter = TicketHeldRecyclerView()
        binding.layoutTicketHeld.activeBookingRecycelView.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = tickedHeldAdapter
        }
    }

    private fun stateObserver() {
        collectLatestFlow(ticketHeldViewModel.state) { state ->
            binding.progressBar.root.isVisible = state.isLoading
            binding.emptyTicketHeld.root.isVisible = state.userTickets.tickets.isEmpty()

            tickedHeldAdapter.submitList(state.userTickets.tickets.toList())
        }
    }

    private fun swipeAndDelete() {
        val swipeAndDelete = object : SwipeAndDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = tickedHeldAdapter.currentList[position]

                ticketHeldViewModel.event(
                    TicketHeldEvent.UpdateTicket(
                        screeningId = itemToDelete.screeningId,
                        seats = itemToDelete.seatNumbers.split(","),
                    )
                )

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeAndDelete)
        itemTouchHelper.attachToRecyclerView(binding.layoutTicketHeld.activeBookingRecycelView)
    }

    private fun eventObserver() {
        collectLatestFlow(ticketHeldViewModel.uiEvents) { event ->
            when (event) {
                is TicketHeldSideEffect.NavigateToProfileFragment -> {
                    // Implement navigation to profile fragment
                    findNavController().navigateUp()
                }

                is TicketHeldSideEffect.ShowError -> binding.root.showSnackBar(getString(event.message))
                is TicketHeldSideEffect.NavigateToPaymentFragment -> NavigationCommands.navigateToPayment(
                    navController = findNavController(),
                    screeningId = event.screeningId,
                    totalPrice = event.ticketPrice,
                    seats = event.seatNumbers.toTypedArray()
                )

                TicketHeldSideEffect.SuccessfulDelete -> {
                    binding.root.showSnackBar(
                        getString(R.string.your_ticket_has_been_deleted),
                        backgroundColor = R.color.green,
                        textColor = R.color.white
                    )
                }
            }
        }
    }
}