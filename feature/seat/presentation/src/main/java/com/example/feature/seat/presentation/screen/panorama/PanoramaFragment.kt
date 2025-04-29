package com.example.feature.seat.presentation.screen.panorama

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.seat.presentation.databinding.FragmentPanoramaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PanoramaFragment :
    BaseFragment<FragmentPanoramaBinding>(FragmentPanoramaBinding::inflate) {

    private val args: PanoramaFragmentArgs by navArgs()
    private val viewModel: PanoramaViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun setUp() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
        }
        binding.webView.webChromeClient = WebChromeClient()

        viewModel.handleEvent(
            PanoramaEvent.LoadSeatsWithPanorama(
                screeningId = args.screeningId,
                seats = args.seats
            )
        )

        observeViewModel()
    }

    private fun observeViewModel() {
        collectLatestFlow(viewModel.uiState) {
            viewModel.uiState.collect { state ->
                renderUiState(state)
            }
        }


        collectLatestFlow(viewModel.sideEffect) {
            viewModel.sideEffect.collect { effect ->
                handleSideEffect(effect)
            }
        }

    }

    private fun renderUiState(state: PanoramaUiState) {

        binding.apply {
            progressBar.isVisible = state.isLoading
            navigationControls.isVisible = state.hasMultipleSeats
            btnPrevSeat.isEnabled = state.canNavigatePrevious
            btnPrevSeat.alpha = if (state.canNavigatePrevious) 1.0f else 0.5f
            btnNextSeat.isEnabled = state.canNavigateNext
            btnNextSeat.alpha = if (state.canNavigateNext) 1.0f else 0.5f
        }

        state.currentSeatNumber?.let { seatNumber ->
            binding.txtSeatInfo.text = getString(
                R.string.seat_panorama_indicator,
                seatNumber,
                state.currentSeatIndex + 1,
                state.panoramaSeats.size
            )
        }

        state.currentSeatPanoramaUrl?.let { url ->
            loadPanorama(url)
        }

    }

    private fun handleSideEffect(effect: PanoramaSideEffect) {
        when (effect) {
            is PanoramaSideEffect.ShowError -> {
                binding.root.showSnackBar(getString(effect.message), backgroundColor = R.color.red)
            }

            is PanoramaSideEffect.NavigatedToNewSeat -> {
                binding.webView.animateAlpha(0.5f, 1.0f, 300)
            }
        }
    }

    private fun loadPanorama(panoramaUrl: String) {
        val panoramaHtml = PanoramaUtil.generateHtml(panoramaUrl)
        binding.webView.loadDataWithBaseURL(
            "https://cdn.jsdelivr.net/",
            panoramaHtml,
            "text/html",
            "utf-8",
            null
        )
    }

    override fun clickListeners() {
        binding.btnArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPrevSeat.setOnClickListener {
            viewModel.handleEvent(
                PanoramaEvent.NavigateToSeat(
                    viewModel.uiState.value.currentSeatIndex - 1
                )
            )
        }

        binding.btnNextSeat.setOnClickListener {
            viewModel.handleEvent(
                PanoramaEvent.NavigateToSeat(
                    viewModel.uiState.value.currentSeatIndex + 1
                )
            )
        }
    }

    override fun onPause() {
        super.onPause()
        binding.webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onDestroy() {
        binding.webView.destroy()
        super.onDestroy()
    }
}