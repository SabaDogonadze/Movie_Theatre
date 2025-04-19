package com.example.movietheatre.feature_profile.presentation.my_shop

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentMyShopBinding
import com.example.movietheatre.feature_profile.presentation.my_shop.main_item_adapter.MainItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyShopFragment : BaseFragment<FragmentMyShopBinding>(FragmentMyShopBinding::inflate) {

    private val viewModel: MyShopViewModel by viewModels()

    private val mainItemAdapter by lazy {
        MainItemAdapter()
    }

    override fun setUp() {
        binding.rvProductCategories.adapter = mainItemAdapter
        collectLatestFlow(viewModel.uiState) { updateUi(it) }
        collectLatestFlow(viewModel.sideEffect) { getSideEffects(it) }

    }

    override fun clickListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            viewModel.onEvent(MyShopEvent.GetUserOrder)
            binding.swipeRefresh.isRefreshing = false


        }
    }


    private fun updateUi(state: MyShopUiState) {
        binding.progressBar.root.isVisible = state.isLoading

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