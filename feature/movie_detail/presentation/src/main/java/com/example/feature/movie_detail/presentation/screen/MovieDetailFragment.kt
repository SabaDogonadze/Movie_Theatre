package com.example.feature.movie_detail.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.loadImg
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.movie_detail.presentation.databinding.FragmentMovieDetailBinding
import com.example.feature.movie_detail.presentation.event.MovieDetailEvent
import com.example.feature.movie_detail.presentation.event.MovieDetailSideEffect
import com.example.navigation.NavigationCommands
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(
        FragmentMovieDetailBinding::inflate
    ) {
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var actorsAdapter: MovieDetailActorsRecyclerView
    private lateinit var screeningsAdapter: MovieDetailScreeningsRecyclerView
    private lateinit var screeningChooserAdapter: ScreeningChooserAdapter


    private val args: MovieDetailFragmentArgs by navArgs()
    override fun setUp() {
        stateObserver()
        eventObserver()
        movieDetailViewModel.event(MovieDetailEvent.GetMovieDetails(args.movieId))
        setUpActorsRecycler()
        setUpScreeningsRecycler()
        clickListeners()
        setUpScreeningChooseRecycler()
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
        binding.layoutMovieDetail.ivMovieImage.setOnClickListener {
            NavigationCommands.navigateToMovieQuizGraph(findNavController())
        }

        binding.noNetwork.btnRetry.setOnClickListener {
            movieDetailViewModel.event(MovieDetailEvent.GetMovieDetails(args.movieId))
        }
    }

    private fun setUpActorsRecycler() {
        actorsAdapter = MovieDetailActorsRecyclerView()
        binding.layoutMovieDetail.actorsRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsAdapter
        }
    }

    private fun setUpScreeningChooseRecycler() {
        screeningChooserAdapter = ScreeningChooserAdapter(onClick = {
            movieDetailViewModel.event(MovieDetailEvent.OnChangedScreeningChooser(it.number))
        })
        binding.layoutMovieDetail.rvScreenDateCategory.apply {
            adapter = screeningChooserAdapter
        }
    }

    private fun setUpScreeningsRecycler() {
        screeningsAdapter = MovieDetailScreeningsRecyclerView()
        binding.layoutMovieDetail.screeningsRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = screeningsAdapter
        }
    }

    private fun stateObserver() {
        collectLatestFlow(movieDetailViewModel.state) { state ->
            Log.d("detailsate", state.toString())
            binding.progressBar.root.isVisible = state.isLoading
            binding.layoutMovieDetail.root.isVisible = !state.isLoading

            actorsAdapter.submitList(state.detailedMovie.actors.toList())
            screeningsAdapter.submitList(state.detailedMovie.screeningsFiltered.toList())
            screeningChooserAdapter.submitList(state.detailedMovie.screeningsChooser.toList())

            binding.layoutMovieDetail.apply {


                ivMovieImage.loadImg(state.detailedMovie.movieImgUrl)

                tvMovieTitle.text = state.detailedMovie.title
                tvMovieDuration.text = state.detailedMovie.duration.toString()
                tvMovieDirector.text = state.detailedMovie.director
                tvMovieImdb.text = state.detailedMovie.imdbRating.toString()
                tvActualAgeRestriction.text = state.detailedMovie.ageRestriction
                tvMovieDescription.text = state.detailedMovie.description
                tvMovieGenre.text = state.detailedMovie.genres.joinToString(",") { it.name }
                tvMovieScreenings.isVisible = state.detailedMovie.screenings.isNotEmpty()
            }
            val embedUrl = convertToEmbedUrl(state.detailedMovie.youtubeUrl)
            loadVideoInWebView(embedUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadVideoInWebView(videoUrl: String) {
        binding.layoutMovieDetail.webView.apply {
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
                is MovieDetailSideEffect.NavigateToBookingFragment -> NavigationCommands.navigateToSeatSelection(
                    navController = findNavController(),
                    screeningId = event.movieId,
                    ticketPrice = event.moviePrice
                )

                is MovieDetailSideEffect.ShowError -> {
                    when (event.message) {
                        com.example.resource.R.string.connection_problem -> {
                            binding.layoutMovieDetail.root.isVisible = false
                            binding.noNetwork.root.isVisible = true
                        }

                        else -> binding.root.showSnackBar(getString(event.message))

                    }

                }
            }
        }
    }

}