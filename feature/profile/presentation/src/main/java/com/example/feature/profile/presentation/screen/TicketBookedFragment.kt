package com.example.feature.profile.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.SwipeAndDeleteCallback
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.profile.presentation.databinding.FragmentTicketBookedBinding
import com.example.feature.profile.presentation.event.TicketBookedEvent
import com.example.feature.profile.presentation.event.TicketBookedSideEffect
import com.example.resource.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketBookedFragment :
    BaseFragment<FragmentTicketBookedBinding>(FragmentTicketBookedBinding::inflate) {

    private val ticketBookedViewModel: TicketBookedViewModel by viewModels()
    private lateinit var tickedBookedAdapter: TicketBookedRecyclerView

    override fun setUp() {

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
            binding.progressBar.root.isVisible = state.isLoading
            tickedBookedAdapter.submitList(state.userTickets.tickets.toList())
        }
    }


    private fun swipeAndDelete() {
        val swipeAndDelete = object : SwipeAndDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemToDelete = tickedBookedAdapter.currentList[position]
                val bookingId = itemToDelete.bookingId
                ticketBookedViewModel.event(TicketBookedEvent.DeleteTicket(bookingId = bookingId))


            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeAndDelete)
        itemTouchHelper.attachToRecyclerView(binding.purchasedTicketsRecyclerView)
    }

    private fun eventObserver() {
        collectLatestFlow(ticketBookedViewModel.uiEvents) { event ->
            when (event) {
                is TicketBookedSideEffect.ShowError -> binding.root.showSnackBar(
                    getString(event.message),
                    backgroundColor = R.color.red
                )

                TicketBookedSideEffect.NavigateToQrFragment -> findNavController().navigate(
                    TicketBookedFragmentDirections.actionTicketBookedFragmentToQrCodeFragment()
                )

                TicketBookedSideEffect.SuccessfulDelete -> {
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