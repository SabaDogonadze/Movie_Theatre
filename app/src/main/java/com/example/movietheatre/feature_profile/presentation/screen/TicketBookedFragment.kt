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
import com.example.movietheatre.databinding.FragmentTicketBookedBinding
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedEvent
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedSideEffect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketBookedFragment :
    BaseFragment<FragmentTicketBookedBinding>(FragmentTicketBookedBinding::inflate) {

    private val ticketBookedViewModel: TicketBookedViewModel by viewModels()
    private lateinit var tickedBookedAdapter: TicketBookedRecyclerView
    private val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun setUp() {
        ticketBookedViewModel.event(
            TicketBookedEvent.GetTickets(
                userId = currentUser?.uid ?: "",
                ticketStatus = TicketStatus.BOOKED.toString().uppercase()
            )
        )
        stateObserver()
        eventObserver()
        setUpActorsRecycler()
        swipeAndDelete()
    }

    override fun clickListeners() {
        tickedBookedAdapter.setonItemClickedListener {
            ticketBookedViewModel.event(TicketBookedEvent.TicketItemClicked)
        }
    }


    private fun setUpActorsRecycler() {
        tickedBookedAdapter = TicketBookedRecyclerView()
        binding.purchasedTicketsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = tickedBookedAdapter
        }
    }

    private fun stateObserver() {
        collectLatestFlow(ticketBookedViewModel.state) { state ->
            // binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            tickedBookedAdapter.submitList(state.userTickets.tickets)
        }
    }


    private fun swipeAndDelete() {
        val swipeAndDelete = object : SwipeAndDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = tickedBookedAdapter.currentList[position]
                val bookingId = itemToDelete.bookingId
                ticketBookedViewModel.event(TicketBookedEvent.DeleteTicket(bookingId = bookingId))
                val updatedList = tickedBookedAdapter.currentList.toMutableList()
                updatedList.removeAt(position)
                tickedBookedAdapter.submitList(updatedList)
                binding.root.showSnackBar(
                    "Your Ticket Has Been Deleted",
                    backgroundColor = R.color.red,
                    textColor = R.color.white
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeAndDelete)
        itemTouchHelper.attachToRecyclerView(binding.purchasedTicketsRecyclerView)
    }

    private fun eventObserver() {
        collectLatestFlow(ticketBookedViewModel.uiEvents) { event ->
            when (event) {
                TicketBookedSideEffect.NavigateToProfileFragment -> TODO()
                is TicketBookedSideEffect.ShowError -> TODO()
                TicketBookedSideEffect.NavigateToQrFragment -> findNavController().navigate(
                    TicketBookedFragmentDirections.actionTicketBookedFragmentToQrCodeFragment()
                )

                TicketBookedSideEffect.TicketUpdatedSuccessfully -> {
                    ticketBookedViewModel.event(
                        TicketBookedEvent.GetTickets(
                            userId = currentUser?.uid ?: "",
                            ticketStatus = TicketStatus.HELD.toString().uppercase()
                        )
                    )
                }
            }
        }
    }

}