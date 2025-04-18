package com.example.movietheatre.feature_shop.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feature_shop.domain.use_case.GetProductUseCase
import com.example.movietheatre.feature_shop.presentation.mapper.groupByCategory
import com.example.movietheatre.feature_shop.presentation.model.Product
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
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShopUiState> = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ShopSideEffect>()
    val sideEffect: SharedFlow<ShopSideEffect> = _sideEffect.asSharedFlow()

    init {
        onEvent(ShopEvent.GetProducts)
    }

    fun onEvent(event: ShopEvent) {
        when (event) {
            ShopEvent.GetProducts -> getProducts()
            is ShopEvent.AddProduct -> addProduct(event.product)
            is ShopEvent.RemoveProduct -> removeProduct(event.product)
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