package com.example.feature.shop.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.GetCoinsUseCase
import com.example.core.domain.use_case.UpdateCoinUseCase
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.feature.shop.domain.model.OrderItem
import com.example.feature.shop.domain.use_case.CreateOrderUseCase
import com.example.feature.shop.domain.use_case.GetProductUseCase
import com.example.feature.shop.presentation.mapper.groupByCategory
import com.example.feature.shop.presentation.model.Product
import com.example.resource.R
import com.google.firebase.auth.FirebaseAuth
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
class ShopViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val updateCoinUseCase: UpdateCoinUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShopUiState> = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ShopSideEffect>()
    val sideEffect: SharedFlow<ShopSideEffect> = _sideEffect.asSharedFlow()

    init {
        onEvent(ShopEvent.GetProducts)
        onEvent(ShopEvent.GetCoin)
    }

    fun onEvent(event: ShopEvent) {
        when (event) {
            ShopEvent.GetProducts -> getProducts()
            is ShopEvent.AddProduct -> addProduct(event.product)
            is ShopEvent.RemoveProduct -> removeProduct(event.product)
            ShopEvent.Order -> order()
            ShopEvent.GetCoin -> getCoins()
            ShopEvent.BuyWithCoin -> buyWithCoin()
        }
    }

    private fun buyWithCoin() {
        val totalPriceCoin =
            (_uiState.value.selectedProduct.sumOf { product -> product.price * product.quantity }
                .toFloat() * 100).toInt()

        viewModelScope.launch {
            if (_uiState.value.userCoins < totalPriceCoin) {
                _sideEffect.emit(ShopSideEffect.ShowError(R.string.you_don_t_have_enough_coins))
            } else {
                when (val result = updateCoinUseCase(-totalPriceCoin)) {
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _sideEffect.emit(ShopSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is Resource.Success -> {
                        onEvent(ShopEvent.Order)
                    }
                }
            }

        }


    }


    private fun getCoins() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getCoinsUseCase()) {
                is Resource.Error -> {
                    _sideEffect.emit(ShopSideEffect.ShowError(result.error.asStringResource()))
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userCoins = result.data.coins
                        )
                    }
                }
            }
        }
    }

    private fun order() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = createOrderUseCase(
                userId = firebaseAuth.currentUser!!.uid,
                orderItems = _uiState.value.selectedProduct.map {
                    OrderItem(
                        it.id,
                        it.quantity
                    )
                }
            )) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(ShopSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(ShopSideEffect.SuccessfulOrder(result.data.trackingCode))
                }
            }
        }
    }

    private fun getProducts() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getProductUseCase()) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(ShopSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            products = result.data.groupByCategory()
                        )
                    }
                }
            }
        }
    }

    private fun addProduct(product: Product) {
        val currentSelectedProducts = _uiState.value.selectedProduct.toMutableList()

        val existingProductIndex = currentSelectedProducts.indexOfFirst { it.id == product.id }

        if (existingProductIndex != -1) {
            val existingProduct = currentSelectedProducts[existingProductIndex]

            val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
            currentSelectedProducts[existingProductIndex] = updatedProduct
        } else {
            currentSelectedProducts.add(product.copy(quantity = 1))
        }

        _uiState.update { it.copy(selectedProduct = currentSelectedProducts) }

    }

    private fun removeProduct(product: Product) {
        val currentSelectedProducts = _uiState.value.selectedProduct.toMutableList()

        val existingProductIndex = currentSelectedProducts.indexOfFirst { it.id == product.id }

        if (existingProductIndex != -1) {
            val existingProduct = currentSelectedProducts[existingProductIndex]

            if (existingProduct.quantity > 1) {
                val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity - 1)
                currentSelectedProducts[existingProductIndex] = updatedProduct
            } else {
                currentSelectedProducts.removeAt(existingProductIndex)
            }

            _uiState.update { it.copy(selectedProduct = currentSelectedProducts) }

        }
    }
}