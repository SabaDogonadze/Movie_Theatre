package com.example.movietheatre.feature_profile.presentation.screen

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.use_case.UpdateTicketUseCase
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.core.domain.use_case.DeleteUsersTicketUserCase
import com.example.movietheatre.core.domain.use_case.GetUserTicketsByStatusUseCase
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedSideEffect
import com.example.movietheatre.feature_profile.presentation.event.TicketHeldEvent
import com.example.movietheatre.feature_profile.presentation.event.TicketHeldSideEffect
import com.example.movietheatre.feature_profile.presentation.mapper.toPresenter
import com.example.movietheatre.feature_profile.presentation.state.TicketHeldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketHeldViewModel @Inject constructor(
    private val getUserTicketsByStatusUseCase: GetUserTicketsByStatusUseCase,
    private val deleteUsersTicketUserCase: DeleteUsersTicketUserCase,
    private val updateTicketUseCase : UpdateTicketUseCase,
) :
    ViewModel() {
    private val _state = MutableStateFlow(TicketHeldState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<TicketHeldSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun event(event: TicketHeldEvent) {
        when (event) {
            is TicketHeldEvent.GetTickets -> getUserTicket(
                userId = event.userId,
                status = event.ticketStatus
            )

            is TicketHeldEvent.TicketItemClicked -> navigateToPaymentFragment(
                screeningId = event.screeningId,
                ticketPrice = event.ticketPrice,
                seatNumbers = event.seatNumbers
            )

            is TicketHeldEvent.DeleteTicket -> deleteUserTicket(bookingId = event.bookingId)
            is TicketHeldEvent.UpdateTicket -> updateTicket(
                screeningId = event.screeningId,
                seats = event.seats,
                status = event.status,
                userId = event.userId
            )
        }
    }

    private fun getUserTicket(userId: String, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getUserTicketsByStatusUseCase.invoke(userId = userId, status = status)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {  // stops loading
                            _state.update {
                                it.copy(isLoading = false)
                            }
                            d("TicketBookedViewModel", "Error: ${result.error}")
                            _uiEvents.emit(TicketHeldSideEffect.ShowError(result.error.asStringResource()))
                        }

                        is Resource.Success -> {
                            val userTickets = result.data.toPresenter()
                            d("TicketBookedViewModel", " ${result}")
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    userTickets = userTickets
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun deleteUserTicket(bookingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            deleteUsersTicketUserCase.invoke(bookingId = bookingId).collect { result ->
                when (result) {
                    is Resource.Error -> {  // stops loading
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        d("TicketBookedViewModel", "Error: ${result.error}")
                        _uiEvents.emit(TicketHeldSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is Resource.Success -> {
                        val ticketStatus = result.data.deletedTicketStatus
                        _uiEvents.emit(TicketHeldSideEffect.TicketUpdatedSuccessfully)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isTicketDeleted = ticketStatus
                            )
                        }
                    }
                }
            }
        }
    }

    private fun navigateToPaymentFragment(screeningId: Int, ticketPrice: Float,seatNumbers:List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvents.emit(
                TicketHeldSideEffect.NavigateToPaymentFragment(
                    screeningId = screeningId,
                    ticketPrice = ticketPrice,
                    seatNumbers = seatNumbers
                )
            )
        }
    }

    private fun updateTicket(screeningId: Int, seats: List<String>, status: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val result = updateTicketUseCase.invoke(
                screeningId = screeningId,
                seats = seats,
                status = status,
                userId = userId
            )

            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    d("TicketHeldViewModel", "Error updating ticket: ${result.error}")
                    _uiEvents.emit(TicketHeldSideEffect.ShowError(result.error.asStringResource()))
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isTicketUpdated = true
                        )
                    }
                    _uiEvents.emit(TicketHeldSideEffect.TicketUpdatedSuccessfully)
                }
            }
        }
    }
}