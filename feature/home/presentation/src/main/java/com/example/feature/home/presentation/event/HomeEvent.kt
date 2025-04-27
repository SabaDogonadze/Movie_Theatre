package com.example.feature.home.presentation.event

import com.example.feature.home.presentation.state.TimeFilter
import java.time.LocalDateTime

sealed interface HomeEvent {
    data object LoadMovies : HomeEvent
    data object LoadGenres : HomeEvent
    data object LoadUpcomingMovies : HomeEvent
    data object LoadPopularMovies : HomeEvent

    data object RefreshLayout : HomeEvent
    data class SearchMovies(val movieTitle: String) : HomeEvent
    data class FilterMoviesByTime(
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val timeFilter: TimeFilter,
    ) : HomeEvent

    data object ClearFilterMoviesByTime : HomeEvent
    data class FilterMoviesByGenre(val genreId: Int) : HomeEvent
}

