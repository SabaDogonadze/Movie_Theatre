package com.example.movietheatre.feature_home.presentation.state

import com.example.movietheatre.feature_home.presentation.model.GenresListUi
import com.example.movietheatre.feature_home.presentation.model.HomeMovieListUi

data class HomeState(
    val isLoading: Boolean = false,
    val movies: List<HomeMovieListUi> = emptyList(),
    val upcomingMovies: List<HomeMovieListUi> = emptyList(),
    val genres: List<GenresListUi> = emptyList(),
    val selectedGenreId: Int? = null,
    val search: String? = null,
    val selectedTimeFilter: TimeFilter = TimeFilter.NONE,
)

enum class TimeFilter {
    NONE,
    MORNING,
    AFTERNOON,
    NIGHT
}