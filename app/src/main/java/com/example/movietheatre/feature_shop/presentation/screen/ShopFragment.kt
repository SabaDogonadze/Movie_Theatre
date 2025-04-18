package com.example.movietheatre.feature_shop.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentShopBinding
import com.example.movietheatre.feature_shop.presentation.extension.calculateTotalPrice
import com.example.movietheatre.feature_shop.presentation.extension.formatSelectedProducts
import com.example.movietheatre.feature_shop.presentation.screen.category_product_adapter.CategoryProductAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopBinding>(FragmentShopBinding::inflate) {
    private val viewModel: ShopViewModel by viewModels()

    private val categoryProductAdapter by lazy {
        CategoryProductAdapter(
            onAdd = { viewModel.onEvent(ShopEvent.AddProduct(it)) },
            onRemove = { viewModel.onEvent(ShopEvent.RemoveProduct(it)) })
    }

    override fun setUp() {
        binding.rvProductCategories.adapter = categoryProductAdapter

        collectLatestFlow(viewModel.uiState) {
            updateUi(it)
        }

        collectLatestFlow(viewModel.sideEffect) { getSideEffects(it) }
    }

    override fun clickListeners() {
        binding.btnPlaceOrder.setOnClickListener {

        }
    }

    private fun getSideEffects(sideEffect: ShopSideEffect) {
        when (sideEffect) {
            is ShopSideEffect.ShowError -> binding.root.showSnackBar(
                getString(sideEffect.message), backgroundColor = R.color.red
            )
        }
    }

    private fun updateUi(state: ShopUiState) {
        binding.progressBar.root.isVisible = state.isLoading

        categoryProductAdapter.submitList(state.products)

        binding.cartSummaryContainer.isVisible = state.selectedProduct.isNotEmpty()
        binding.txtCartItems.text = state.selectedProduct.formatSelectedProducts()
        binding.txtTotalPrice.text = state.selectedProduct.calculateTotalPrice()

    }

}