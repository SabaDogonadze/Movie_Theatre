package com.example.movietheatre.feature_profile.presentation.screen

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.SwipeAndDeleteCallback
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.databinding.FragmentTicketHeldBinding
import com.example.movietheatre.feature_profile.presentation.event.TicketHeldEvent
import com.example.movietheatre.feature_profile.presentation.event.TicketHeldSideEffect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketHeldFragment :
    BaseFragment<FragmentTicketHeldBinding>(FragmentTicketHeldBinding::inflate) {
    private val ticketHeldViewModel: TicketHeldViewModel by viewModels()
    private lateinit var tickedHeldAdapter: TicketHeldRecyclerView
    private val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    override fun setUp() {
        ticketHeldViewModel.event(
            TicketHeldEvent.GetTickets(
                userId = currentUser?.uid ?: "",
                ticketStatus = TicketStatus.HELD.toString().uppercase()
            )
        )
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
        binding.activeBookingRecycelView.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = tickedHeldAdapter
        }
    }

    private fun stateObserver() {
        collectLatestFlow(ticketHeldViewModel.state) { state ->
            // binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            tickedHeldAdapter.submitList(state.userTickets.tickets)
        }
    }

    private fun swipeAndDelete() {
        val swipeAndDelete = object : SwipeAndDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = tickedHeldAdapter.currentList[position]
                val bookingId = itemToDelete.bookingId


                ticketHeldViewModel.event(TicketHeldEvent.DeleteTicket(bookingId = bookingId))

                val updatedList = tickedHeldAdapter.currentList.toMutableList()
                updatedList.removeAt(position)
                tickedHeldAdapter.submitList(updatedList)


                ticketHeldViewModel.event(
                    TicketHeldEvent.UpdateTicket(
                        screeningId = itemToDelete.screeningId,
                        seats = itemToDelete.seatNumbers.split(","),
                        status = TicketStatus.FREE.toString().uppercase(),
                        userId = currentUser?.uid ?: ""
                    )
                )
                binding.root.showSnackBar(
                    "Your Ticket Has Been Deleted",
                    backgroundColor = R.color.red,
                    textColor = R.color.white
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeAndDelete)
        itemTouchHelper.attachToRecyclerView(binding.activeBookingRecycelView)
    }

    private fun eventObserver() {
        collectLatestFlow(ticketHeldViewModel.uiEvents) { event ->
            when (event) {
                is TicketHeldSideEffect.NavigateToProfileFragment -> {
                    // Implement navigation to profile fragment
                    findNavController().navigate(
                        binding.root.showSnackBar(
                            "Your Ticket Has Been Deleted",
                            backgroundColor = R.color.red,
                            textColor = R.color.white
                        )
                    )
                }

                is TicketHeldSideEffect.ShowError -> binding.root.showSnackBar(getString(event.message))
                is TicketHeldSideEffect.NavigateToPaymentFragment -> findNavController().navigate(
                    TicketHeldFragmentDirections.actionTicketHeldFragmentToPaymentFragment(
                        screeningId = event.screeningId,
                        totalPrice = event.ticketPrice,
                        seats = event.seatNumbers.toTypedArray()
                    )
                )

                is TicketHeldSideEffect.TicketUpdatedSuccessfully -> {
                    ticketHeldViewModel.event(
                        TicketHeldEvent.GetTickets(
                            userId = currentUser?.uid ?: "",
                            ticketStatus = TicketStatus.HELD.toString().uppercase()
                        )
                    )
                }
            }
        }
    }
}