package com.example.movietheatre.feature_payment.presentation.screen.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import com.example.movietheatre.R
import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentPaymentBinding
import com.example.movietheatre.feature_payment.presentation.screen.payment.adapter.PaymentPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val args: PaymentFragmentArgs by navArgs()
    val viewModel: PaymentViewModel by viewModels()
    private val paymentPagerAdapter: PaymentPagerAdapter by lazy {
        PaymentPagerAdapter(onClick = {
            viewModel.onEvent(PaymentEvent.OnDeleteCardClick(it))
        })
    }
    private lateinit var googlePayFragment: GooglePayFragment
    private lateinit var paymentResultLauncher: ActivityResultLauncher<IntentSenderRequest>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            val resultCode = result.resultCode
            val data = result.data
            viewModel.onEvent(
                PaymentEvent.OnGoogleBuy(
                    resultCode,
                    data,
                    args.screeningId,
                    args.seats.toList(),
                    args.totalPrice.toDouble()
                )
            )
            Log.d("googlepay", resultCode.toString() + data.toString())
            if (::googlePayFragment.isInitialized) {
                googlePayFragment.handlePaymentResult(resultCode, data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        clickListeners()
        if (savedInstanceState == null) {
            googlePayFragment = GooglePayFragment.newInstance()

            childFragmentManager.beginTransaction()
                .replace(R.id.google_pay_container, googlePayFragment)
                .commit()
        } else {
            googlePayFragment =
                childFragmentManager.findFragmentById(R.id.google_pay_container) as GooglePayFragment
        }

        childFragmentManager.executePendingTransactions()

        googlePayFragment.setActivityResultLauncher(paymentResultLauncher)
        googlePayFragment.setTotalPrice(args.totalPrice.toString())

    }

    private fun setUp() {
        viewModel.onEvent(PaymentEvent.LoadCards)
        binding.txtTotalValue.text = args.totalPrice.asMoneyFormat()
        setUpAdapter()

        collectLatestFlow(viewModel.uiState) { updateUiState(it) }
        collectLatestFlow(viewModel.sideEffect) { getEffects(it) }

    }

    private fun clickListeners() {
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
                binding.btnBuyTickets.isVisible = false
                val transition = AutoTransition().apply {
                    duration = 300
                }

                TransitionManager.beginDelayedTransition(binding.root, transition)
                binding.SuccessBuyLayout.root.visibility = View.VISIBLE
            }
        }
    }


    private fun updateUiState(state: PaymentUiState) {
        paymentPagerAdapter.submitList(state.cards.toList())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}