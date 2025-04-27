package com.example.feature.shop.presentation.screen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.shop.presentation.databinding.FragmentShopBinding
import com.example.feature.shop.presentation.extension.calculateTotalPrice
import com.example.feature.shop.presentation.extension.formatSelectedProducts
import com.example.feature.shop.presentation.screen.category_product_adapter.CategoryProductAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopBinding>(FragmentShopBinding::inflate) {
    private val viewModel: ShopViewModel by viewModels()

    private val categoryProductAdapter by lazy {
        CategoryProductAdapter(onAdd = { viewModel.onEvent(ShopEvent.AddProduct(it)) },
            onRemove = { viewModel.onEvent(ShopEvent.RemoveProduct(it)) })
    }

    override fun setUp() {
        binding.layoutShop.rvProductCategories.adapter = categoryProductAdapter

        collectLatestFlow(viewModel.uiState) {
            updateUi(it)
        }

        collectLatestFlow(viewModel.sideEffect) { getSideEffects(it) }
    }

    override fun clickListeners() {
        binding.layoutShop.apply {
            btnPlaceOrder.setOnClickListener {
                viewModel.onEvent(ShopEvent.Order)
            }
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = true
                viewModel.onEvent(ShopEvent.GetProducts)
                swipeRefresh.isRefreshing = false

            }
            btnBuyWithCoin.setOnClickListener {
                viewModel.onEvent(ShopEvent.BuyWithCoin)
            }
        }

        binding.noNetwork.apply {
            btnRetry.setOnClickListener {
                viewModel.onEvent(ShopEvent.RefreshLayout)
            }
        }
    }

    private fun getSideEffects(sideEffect: ShopSideEffect) {
        when (sideEffect) {
            is ShopSideEffect.ShowError -> {
                when (sideEffect.message) {
                    R.string.connection_problem -> {
                        binding.layoutShop.root.isVisible = false
                        binding.noNetwork.root.isVisible = true
                    }

                    else -> binding.root.showSnackBar(
                        getString(sideEffect.message), backgroundColor = R.color.red
                    )
                }
            }

            is ShopSideEffect.SuccessfulOrder -> showTrackingCodeDialog(trackingCode = sideEffect.trackingCode.toString())
        }
    }

    private fun updateUi(state: ShopUiState) {
        binding.progressBar.root.isVisible = state.isLoading
        binding.layoutShop.root.isVisible = !state.isLoading && state.products.isNotEmpty()

        categoryProductAdapter.submitList(state.products)

        binding.layoutShop.apply {
            cartSummaryContainer.isVisible = state.selectedProduct.isNotEmpty()
            txtCartItems.text = state.selectedProduct.formatSelectedProducts()
            txtTotalPrice.text = state.selectedProduct.calculateTotalPrice()
            totalCoins.txtCoinCount.text = state.userCoins.toString()
        }
    }

    private fun showTrackingCodeDialog(trackingCode: String) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(getString(R.string.order_successful))
        builder.setMessage(getString(R.string.tracking_code, trackingCode))
        builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
            findNavController().navigateUp()
        }
        builder.setCancelable(false)
        builder.show()
    }

}