package com.example.feature.home.presentation.screen.home

import android.util.Log.d
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.presentation.BaseFragment
import com.example.core.presentation.R
import com.example.core.presentation.extension.collectLatestFlow
import com.example.core.presentation.extension.hideKeyboard
import com.example.core.presentation.extension.showSnackBar
import com.example.feature.home.presentation.databinding.FragmentHomeBinding
import com.example.feature.home.presentation.event.HomeEvent
import com.example.feature.home.presentation.event.HomeSideEffect
import com.example.feature.home.presentation.state.TimeFilter
import com.example.feature.home.presentation.util.getTimeRangeForInterval
import com.example.navigation.NavigationCommands
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeMovieAdapter: HomeMovieRecyclerAdapter
    private lateinit var genreAdapter: GenreRecyclerAdapter
    private lateinit var upComingMoviesAdapter: UpComingMoviesAdapter
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter


    override fun setUp() {
        stateObserver()
        eventObserver()
        setUpMovieRecycler()
        setUpGenreRecycler()
        setUpUpcomingMovieRecycler()
        setUpSearch()
        clickListeners()
        setUpPopularMovieRecycler()
    }

    override fun clickListeners() {
        homeMovieAdapter.setonItemClickedListener {
            navigateToMovieDetailFragment(it.id)
        }
        setupTimeFilterClickListener(
            binding.homeContentView.btnMorningTime,
            TimeFilter.MORNING,
            "11:00-15:00"
        )
        setupTimeFilterClickListener(
            binding.homeContentView.btnAfternoonTime,
            TimeFilter.AFTERNOON,
            "15:00-19:00"
        )
        setupTimeFilterClickListener(
            binding.homeContentView.btnNightTime,
            TimeFilter.NIGHT,
            "19:00-00:00"
        )

        genreAdapter.setonItemClickedListener { genre ->
            homeViewModel.event(HomeEvent.FilterMoviesByGenre(genre.id))
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.event(HomeEvent.RefreshLayout)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.noNetworkView.btnRetry.setOnClickListener {
            homeViewModel.event(HomeEvent.RefreshLayout)
        }

    }

    // setting up MovieRecycler, Casual Implementation
    private fun setUpMovieRecycler() {
        homeMovieAdapter = HomeMovieRecyclerAdapter()
        binding.homeContentView.movieRecyclerAdapter.apply {
            // layoutManager = LinearLayoutManager(requireContext())
            adapter = homeMovieAdapter
        }
    }

    private fun setUpUpcomingMovieRecycler() {
        upComingMoviesAdapter = UpComingMoviesAdapter(onClick = {
            navigateToMovieDetailFragment(it.id)
        })
        binding.homeContentView.upcomingMovieRecyclerAdapter.apply {
            adapter = upComingMoviesAdapter
        }
    }

    private fun setUpPopularMovieRecycler() {
        popularMoviesAdapter = PopularMoviesAdapter(onClick = {
            navigateToMovieDetailFragment(it.id)
        })
        binding.homeContentView.popularMovieRecyclerAdapter.apply {
            adapter = popularMoviesAdapter
        }
    }

    // setting up GenreRecycler, Casual Implementation
    private fun setUpGenreRecycler() {
        genreAdapter = GenreRecyclerAdapter()
        binding.homeContentView.genresRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }
    }

    // observing state in with viewLifecycleOwner, listening states in which is written in homeViewModel
    // each time state changes (when event is happening like fetching movies, filtering, and so on) the block inside collect {} executes.
    // progressbar state changes if state is loading or not. if it is loading progressbar is visible if it is not, View.GONE
    // passing updating movie list to a homeMovieAdapter
    // updating genres: using map function to iterate each genre in state.genre. -----
    // isSelected is written in GenresListUi model and it is false by default
    // so if genre.id matches the selectedId, it marks as selected, if not it is unselected
    // using copy function because data classes are immutable by default we can not say  genre.isSelected = (genre.id == state.selectedGenreId) // isSelected is Val
    // passing updatedGenres to a genreAdapter
    // calling updateTimeButtonBackgrounds function
    private fun stateObserver() {
        collectLatestFlow(homeViewModel.state) { state ->
            binding.progressBar.root.isVisible = state.isLoading
            binding.homeContentView.root.isVisible = !state.isLoading
            d("recyclermovie", "${state.movies}")
            homeMovieAdapter.submitList(state.movies.toList())
            upComingMoviesAdapter.submitList(state.upcomingMovies.toList())
            genreAdapter.submitList(state.genres.toList())
            popularMoviesAdapter.submitList(state.popularMovies.toList())
            updateTimeButtonBackgrounds(state.selectedTimeFilter)
        }
    }

    // observing uiEvent like errors and navigation. observing event which are send from viewmodel when some event is happening.
    private fun eventObserver() {
        collectLatestFlow(homeViewModel.uiEvents) { event ->
            when (event) {
                is HomeSideEffect.ShowError -> {
                    when (event.message) {
                        R.string.connection_problem -> {
                            binding.homeContentView.root.isVisible = false
                            binding.noNetworkView.root.isVisible = true
                        }

                        else -> binding.root.showSnackBar(getString(event.message))
                    }
                }
            }
        }
    }

    // setting Up Search and it is called in setUp
    // btnSearch has click Listener and there are checks. firstly, we are reading text which are written in edit text, this is our query
    // then there are check if query ( out text ) is not empty, we throw event, if it is empty we throw another event which shows original list of data
    // there are also addTextChangedListener on edit text, this is useful when user clears search. in this case we are throwing event to clear all filtering and show original list.

    private fun setUpSearch() {
        binding.homeContentView.apply {
            btnSearch.setOnClickListener {
                binding.root.hideKeyboard()
                val query = etSearch.text.toString().trim()
                homeViewModel.event(HomeEvent.SearchMovies(query))
            }
            etSearch.addTextChangedListener { editable ->
                if (editable.isNullOrEmpty()) {
                    homeViewModel.event(HomeEvent.SearchMovies(editable.toString()))
                }
            }
        }
    }

    // using findNavController to navigate MovieDetailFragment
    private fun navigateToMovieDetailFragment(movieId: Int) {
        NavigationCommands.navigateToMovieDetail(findNavController(), movieId)
    }

    // we have 3 day times statically written.
    // here buttonBackground updates are happening.
    // in constructor there is selectedTimeFilter which is TimeFilter type, TimeFilter is enum.
    // if this selectedTimeFilter is morning for example, then btnMorning's background is changing. ( this check is happening in if blocks)
    // using ContextCompat.getDrawable which need context to change button's background.

    private fun updateTimeButtonBackgrounds(selectedTimeFilter: TimeFilter) {
        binding.homeContentView.apply {
            btnMorningTime.background = ContextCompat.getDrawable(
                requireContext(),
                if (selectedTimeFilter == TimeFilter.MORNING)
                    R.drawable.bg_selected_time_button
                else
                    R.drawable.bg_time_button
            )
            btnAfternoonTime.background = ContextCompat.getDrawable(
                requireContext(),
                if (selectedTimeFilter == TimeFilter.AFTERNOON)
                    R.drawable.bg_selected_time_button
                else
                    R.drawable.bg_time_button
            )
            btnNightTime.background = ContextCompat.getDrawable(
                requireContext(),
                if (selectedTimeFilter == TimeFilter.NIGHT)
                    R.drawable.bg_selected_time_button
                else
                    R.drawable.bg_time_button
            )
        }
    }

    //  button triggers filter, this button is time buttons,
    // filter is TimeFilter type which is enum ( Morning, Afternoon,Night and so on )
    // interval is string ( for example "11:00-15:00" )
    // homeViewModel.state.value.selectedTimeFilter holds the currently selected time filter ( for example Morning)
    // if the user clicks a different time filter, we proceed to apply the new filter.
    // != why is that? --> this means "if the currently selected filter is different from the clicked filter", then apply the new filter
    // cases -> for example filter is "Morning" , is selectedTimeFilter is Afternoon, it checks afternoon != morning so it returning true and throws event
    // and changes updates selectedTimeFilter in event which we threw.
    // if it is same for example morning == morning returns false, and in this case filter clears and button goes normal condition.

    private fun setupTimeFilterClickListener(
        button: Button,
        filter: TimeFilter,
        interval: String,
    ) {
        button.setOnClickListener {
            if (homeViewModel.state.value.selectedTimeFilter != filter) {
                val (startTime, endTime) = getTimeRangeForInterval(interval) // this is helper function in utils
                homeViewModel.event(HomeEvent.FilterMoviesByTime(startTime, endTime, filter))
            } else {
                homeViewModel.event(HomeEvent.ClearFilterMoviesByTime)
            }
        }
    }
}