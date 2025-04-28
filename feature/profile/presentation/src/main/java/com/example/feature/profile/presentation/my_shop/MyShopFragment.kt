package com.example.feature.profile.presentation.my_shop

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.profile.presentation.databinding.FragmentMyShopBinding
import com.example.feature.profile.presentation.my_shop.main_item_adapter.MainItemAdapter
import com.example.navigation.NavigationCommands
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyShopFragment : BaseFragment<FragmentMyShopBinding>(FragmentMyShopBinding::inflate) {

    private val viewModel: MyShopViewModel by viewModels()

    private val mainItemAdapter by lazy {
        MainItemAdapter()
    }

    override fun setUp() {
        binding.layoutMyShop.rvProductCategories.adapter = mainItemAdapter
        collectLatestFlow(viewModel.uiState) { updateUi(it) }
        collectLatestFlow(viewModel.sideEffect) { getSideEffects(it) }

    }

    override fun clickListeners() {
        binding.layoutMyShop.apply {
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = true
                viewModel.onEvent(MyShopEvent.GetUserOrder)
                swipeRefresh.isRefreshing = false
            }
        }
        binding.emptyMyShop.btnBuyFavoriteProduct.setOnClickListener {
            NavigationCommands.navigateToShopGraph(findNavController())
        }
    }


    private fun updateUi(state: MyShopUiState) {
        binding.progressBar.root.isVisible = state.isLoading
        binding.emptyMyShop.root.isVisible = state.products.isEmpty()

        mainItemAdapter.submitList(
            state.products.toList()
        )
    }

    private fun getSideEffects(sideEffect: MyShopSideEffect) {
        when (sideEffect) {
            is MyShopSideEffect.ShowError -> binding.root.showSnackBar(
                getString(sideEffect.message),
                backgroundColor = R.color.red
            )
        }
    }
}