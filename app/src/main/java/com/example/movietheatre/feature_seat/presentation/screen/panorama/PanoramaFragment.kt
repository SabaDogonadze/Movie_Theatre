package com.example.movietheatre.feature_seat.presentation.screen.panorama

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.databinding.FragmentPanoramaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PanoramaFragment : BaseFragment<FragmentPanoramaBinding>(FragmentPanoramaBinding::inflate) {

    private fun loadPanorama() {

        binding.webView.loadDataWithBaseURL(
            "https://cdn.jsdelivr.net/",
            PanoramaUtil.HTML,
            "text/html",
            "utf-8",
            null
        )
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun setUp() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
        }
        binding.webView.webChromeClient = WebChromeClient()

        loadPanorama()
    }

    override fun clickListeners() {
        binding.btnArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}