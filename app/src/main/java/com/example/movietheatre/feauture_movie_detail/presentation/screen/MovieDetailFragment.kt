package com.example.movietheatre.feauture_movie_detail.presentation.screen

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movietheatre.core.presentation.BaseFragment
import com.example.movietheatre.core.presentation.extension.collectLatestFlow
import com.example.movietheatre.core.presentation.extension.showSnackBar
import com.example.movietheatre.databinding.FragmentMovieDetailBinding
import com.example.movietheatre.feauture_movie_detail.presentation.event.MovieDetailEvent
import com.example.movietheatre.feauture_movie_detail.presentation.event.MovieDetailSideEffect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var actorsAdapter: MovieDetailActorsRecyclerView
    private lateinit var screeningsAdapter: MovieDetailScreeningsRecyclerView
    private val args: MovieDetailFragmentArgs by navArgs()
    override fun setUp() {
        stateObserver()
        eventObserver()
        movieDetailViewModel.event(MovieDetailEvent.GetMovieDetails(args.movieId))
        setUpActorsRecycler()
        setUpScreeningsRecycler()
        clickListeners()
    }

    override fun clickListeners() {
        screeningsAdapter.setonItemClickedListener {
            movieDetailViewModel.event(
                MovieDetailEvent.ScreeningItemClicked(
                    it.id,
                    it.screeningPrice
                )
            )
        }
    }

    private fun setUpActorsRecycler() {
        actorsAdapter = MovieDetailActorsRecyclerView()
        binding.actorsRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
        }
    }

    private fun setUpScreeningsRecycler() {
        screeningsAdapter = MovieDetailScreeningsRecyclerView()
        binding.screeningsRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = screeningsAdapter
        }
    }

    private fun stateObserver() {
        collectLatestFlow(movieDetailViewModel.state) { state ->
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            actorsAdapter.submitList(state.detailedMovie.actors.toList())
            screeningsAdapter.submitList(state.detailedMovie.screenings.toList())
            Glide.with(this)
                .load(state.detailedMovie.movieImgUrl)
                .into(binding.ivMovieImage)
            binding.apply {
                tvMovieTitle.text = state.detailedMovie.title
                tvMovieDuration.text = state.detailedMovie.duration.toString()
                tvMovieDirector.text = state.detailedMovie.director
                tvMovieImdb.text = state.detailedMovie.imdbRating.toString()
                tvActualAgeRestriction.text = state.detailedMovie.ageRestriction
                tvMovieDescription.text = state.detailedMovie.description
            }
            val embedUrl = convertToEmbedUrl(state.detailedMovie.youtubeUrl)
            loadVideoInWebView(embedUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadVideoInWebView(videoUrl: String) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(videoUrl)
        }
    }

    private fun convertToEmbedUrl(youtubeUrl: String): String {
        val videoId = youtubeUrl.substringAfter("v=")
        return "https://www.youtube.com/embed/$videoId"
    }

    private fun eventObserver() {
        collectLatestFlow(movieDetailViewModel.uiEvents) { event ->
            when (event) {
                is MovieDetailSideEffect.NavigateToBookingFragment -> findNavController().navigate(
                    MovieDetailFragmentDirections.actionMovieDetailFragmentToSeatFragment(
                        event.movieId,
                        event.moviePrice
                    )
                )

                is MovieDetailSideEffect.ShowError -> binding.root.showSnackBar(getString(event.message))
            }
        }
    }

}