package com.example.movietheatre.feature_payment.presentation.screen.payment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentPaymentBinding
import com.example.movietheatre.feature_payment.presentation.screen.payment.adapter.PaymentPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private val args: PaymentFragmentArgs by navArgs()
    private val viewModel: PaymentViewModel by viewModels()
    private val paymentPagerAdapter: PaymentPagerAdapter by lazy {
        PaymentPagerAdapter()
    }

    override fun setUp() {
        binding.txtTotalValue.text = args.totalPrice.asMoneyFormat()
        setUpAdapter()
        collectLatestFlow(viewModel.uiState) { updateUiState(it) }
        collectLatestFlow(viewModel.sideEffect) { getEffects(it) }
    }

    override fun clickListeners() {
        binding.txtAddNewCard.setOnClickListener {
            viewModel.onEvent(PaymentEvent.AddNewCardClicked)
        }
        binding.btnBuyTickets.setOnClickListener {
            viewModel.onEvent(
                PaymentEvent.OnBuy(
                    args.screeningId,
                    seats = args.seats.toList(),
                    totalPrice = args.totalPrice.toDouble()
                )
            )
        }
    }

    private fun getEffects(effect: PaymentSideEffect) {
        when (effect) {
            is PaymentSideEffect.NavigateToAddCard -> {
                findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToAddCardFragment())
            }

            is PaymentSideEffect.ShowError -> {
                binding.root.showSnackBar(getString(effect.message), backgroundColor = R.color.red)
            }

            PaymentSideEffect.NavigateToHomeScreen -> {
                findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToIdHomeFragment())
            }

            PaymentSideEffect.SuccessfulPayment -> {
                binding.SuccessBuyLayout.root.isVisible = true
            }
        }
    }


    private fun updateUiState(state: PaymentUiState) {
        paymentPagerAdapter.submitList(state.cards)
        binding.progressBar.root.isVisible = state.isLoading
        binding.btnBuyTickets.isVisible = !state.isLoading
    }

    private fun setUpAdapter() {
        binding.pager.adapter = paymentPagerAdapter

        binding.pager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            val pageMarginPx = 16
            val offsetPx = 32
            setPageTransformer { page, position ->
                val offset = position * -(2 * offsetPx + pageMarginPx)
                if (binding.pager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    page.translationX = offset
                } else {
                    page.translationY = offset
                }
            }
        }
    }
}