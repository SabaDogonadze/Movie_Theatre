package com.example.feature.home.presentation.state

import com.example.feature.home.presentation.model.GenresListUi
import com.example.feature.home.presentation.model.HomeMovieListUi

data class HomeState(
    val isLoading: Boolean = false,
    val isLoadingMovie: Boolean = false,
    val movies: List<HomeMovieListUi> = emptyList(),
    val upcomingMovies: List<HomeMovieListUi> = emptyList(),
    val popularMovies: List<HomeMovieListUi> = emptyList(),
    val genres: List<GenresListUi> = emptyList(),
    val selectedGenreId: Int? = null,
    val search: String? = null,
    val selectedTimeFilter: TimeFilter = TimeFilter.NONE,
) {
    val genresFiltered: List<GenresListUi> = selectedGenreId?.let {
        genres.map { it.copy(isSelected = selectedGenreId == it.id) }
    } ?: genres
}

enum class TimeFilter {
    NONE,
    MORNING,
    AFTERNOON,
    NIGHT
}