package com.example.movietheatre.feature_profile.presentation.screen

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.core.presentation.extension.toDomain
import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.feature_profile.domain.use_case.DeleteUsersTicketUserCase
import com.example.movietheatre.feature_profile.domain.use_case.GetUserTicketsByStatusUseCase
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedEvent
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedSideEffect
import com.example.movietheatre.feature_profile.presentation.mapper.toPresenter
import com.example.movietheatre.feature_profile.presentation.state.TicketBookedState
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
class TicketBookedViewModel @Inject constructor(
    private val getUserTicketsByStatusUseCase: GetUserTicketsByStatusUseCase,
    private val deleteUsersTicketUserCase: DeleteUsersTicketUserCase,
    private val firebaseAuth: FirebaseAuth,
) :
    ViewModel() {
    private val _state = MutableStateFlow(TicketBookedState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<TicketBookedSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()

    init {
        event(TicketBookedEvent.GetTickets)
    }

    fun event(event: TicketBookedEvent) {
        when (event) {
            is TicketBookedEvent.GetTickets -> getUserTicket(
                userId = firebaseAuth.currentUser!!.uid,
                status = TicketStatus.BOOKED.toDomain()
            )

            TicketBookedEvent.TicketItemClicked -> navigateQrFragment()
            is TicketBookedEvent.DeleteTicket -> deleteUserTicket(bookingId = event.bookingId)
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
                    _uiEvents.emit(TicketBookedSideEffect.ShowError(result.error.asStringResource()))

                }

                is Resource.Success -> {
                    val userTickets = result.data.toPresenter()
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

    private fun deleteUserTicket(bookingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            when (val result = deleteUsersTicketUserCase(bookingId)) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    d("TicketBookedViewModel", "Error: ${result.error}")
                    _uiEvents.emit(TicketBookedSideEffect.ShowError(result.error.asStringResource()))

                }

                is Resource.Success -> {
                    _uiEvents.emit(TicketBookedSideEffect.SuccessfulDelete)
                    event(
                        TicketBookedEvent.GetTickets
                    )
                }
            }
        }
    }

    private fun navigateQrFragment() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvents.emit(TicketBookedSideEffect.NavigateToQrFragment)

        }
    }

}