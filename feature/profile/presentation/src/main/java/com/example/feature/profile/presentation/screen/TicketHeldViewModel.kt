package com.example.feature.profile.presentation.screen

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.UpdateTicketUseCase
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.core.presentation.extension.toDomain
import com.example.core.presentation.util.TicketStatus
import com.example.feature.profile.domain.use_case.GetUserTicketsByStatusUseCase
import com.example.feature.profile.presentation.event.TicketHeldEvent
import com.example.feature.profile.presentation.event.TicketHeldSideEffect
import com.example.feature.profile.presentation.mapper.toPresenter
import com.example.feature.profile.presentation.state.TicketHeldState
import com.google.firebase.auth.FirebaseAuth
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
    private val updateTicketUseCase: UpdateTicketUseCase,
    private val firebaseAuth: FirebaseAuth,
) :
    ViewModel() {
    private val _state = MutableStateFlow(TicketHeldState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<TicketHeldSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()

    init {
        event(TicketHeldEvent.GetTickets)
    }

    fun event(event: TicketHeldEvent) {
        when (event) {
            is TicketHeldEvent.GetTickets -> getUserTicket(
                userId = firebaseAuth.currentUser!!.uid,
                status = TicketStatus.HELD.toDomain()
            )

            is TicketHeldEvent.TicketItemClicked -> navigateToPaymentFragment(
                screeningId = event.screeningId,
                ticketPrice = event.totalPrice,
                seatNumbers = event.seatNumbers
            )

            is TicketHeldEvent.UpdateTicket -> updateTicket(
                screeningId = event.screeningId,
                seats = event.seats,
            )
        }
    }

    private fun getUserTicket(userId: String, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            when (val result =
                getUserTicketsByStatusUseCase.invoke(userId = userId, status = status)) {
                is Resource.Error -> {
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

    private fun navigateToPaymentFragment(
        screeningId: Int,
        ticketPrice: Float,
        seatNumbers: List<String>,
    ) {
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

    private fun updateTicket(
        screeningId: Int,
        seats: List<String>,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val result = updateTicketUseCase.invoke(
                screeningId = screeningId,
                seats = seats,
                status = TicketStatus.FREE.toDomain(),
                userId = firebaseAuth.currentUser!!.uid
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
                    _uiEvents.emit(TicketHeldSideEffect.SuccessfulDelete)
                    event(TicketHeldEvent.GetTickets)
                }
            }
        }
    }
}