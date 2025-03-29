package com.example.movietheatre.feature_home.presentation.event

import com.example.movietheatre.feature_home.presentation.state.TimeFilter
import java.time.LocalDateTime

sealed class HomeEvent {
    data object LoadMovies : HomeEvent()
    data object LoadGenres : HomeEvent()
    data class SearchMovies(val movieTitle: String) : HomeEvent()
    data class FilterMoviesByTime(
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val timeFilter: TimeFilter,
    ) : HomeEvent()

    data object ClearFilterMoviesByTime : HomeEvent()
    data class FilterMoviesByGenre(val genreId: Int) : HomeEvent()
    data class ClearMoviesByGenre(val genreId: Int) : HomeEvent()
}

