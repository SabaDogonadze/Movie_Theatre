package com.example.movietheatre.feature_profile.presentation.screen

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feature_profile.domain.use_case.DeleteUsersTicketUserCase
import com.example.movietheatre.feature_profile.domain.use_case.GetUserTicketsByStatusUseCase
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedEvent
import com.example.movietheatre.feature_profile.presentation.event.TicketBookedSideEffect
import com.example.movietheatre.feature_profile.presentation.mapper.toPresenter
import com.example.movietheatre.feature_profile.presentation.state.TicketBookedState
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
class TicketBookedViewModel @Inject constructor(private val getUserTicketsByStatusUseCase: GetUserTicketsByStatusUseCase, private val deleteUsersTicketUserCase: DeleteUsersTicketUserCase) :
    ViewModel() {
    private val _state = MutableStateFlow(TicketBookedState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<TicketBookedSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun event(event: TicketBookedEvent){
        when(event){
            is TicketBookedEvent.GetTickets -> getUserTicket(userId = event.userId, status = event.ticketStatus)
            TicketBookedEvent.TicketItemClicked ->navigateQrFragment()
            is TicketBookedEvent.DeleteTicket -> deleteUserTicket(bookingId = event.bookingId)
        }
    }

    private fun getUserTicket(userId:String,status:String){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getUserTicketsByStatusUseCase.invoke(userId = userId,status=status).collect{result->
                when(result){
                    is Resource.Error -> {  // stops loading
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        d("TicketBookedViewModel", "Error: ${result.error}")
                        _uiEvents.emit(TicketBookedSideEffect.ShowError(result.error.asStringResource()))
                    }
                    is Resource.Success -> {
                        val userTickets = result.data.toPresenter()
                        _uiEvents.emit(TicketBookedSideEffect.TicketUpdatedSuccessfully)
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

    private fun deleteUserTicket(bookingId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            deleteUsersTicketUserCase.invoke(bookingId=bookingId).collect{result->
                when(result){
                    is Resource.Error -> {  // stops loading
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        d("TicketBookedViewModel", "Error: ${result.error}")
                        _uiEvents.emit(TicketBookedSideEffect.ShowError(result.error.asStringResource()))
                    }
                    is Resource.Success -> {
                        val ticketStatus = result.data.deletedTicketStatus
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

    private fun navigateQrFragment(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvents.emit(TicketBookedSideEffect.NavigateToQrFragment)

        }
    }

}