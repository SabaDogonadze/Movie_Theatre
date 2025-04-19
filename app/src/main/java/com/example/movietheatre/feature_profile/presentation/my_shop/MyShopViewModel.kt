package com.example.movietheatre.feature_profile.presentation.my_shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feature_profile.domain.use_case.GetUserOrderUseCase
import com.example.movietheatre.feature_profile.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyShopViewModel @Inject constructor(
    private val getUserOrderUseCase: GetUserOrderUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MyShopUiState> = MutableStateFlow(MyShopUiState())
    val uiState: StateFlow<MyShopUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyShopSideEffect>()
    val sideEffect: SharedFlow<MyShopSideEffect> = _sideEffect.asSharedFlow()


    init {

        onEvent(MyShopEvent.GetUserOrder)
    }

    fun onEvent(event: MyShopEvent) {
        when (event) {
            MyShopEvent.GetUserOrder -> getUserOrder()
        }
    }

    private fun getUserOrder() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getUserOrderUseCase()) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(MyShopSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            products = result.data.toPresentation()
                        )
                    }
                }
            }
        }
    }
}
