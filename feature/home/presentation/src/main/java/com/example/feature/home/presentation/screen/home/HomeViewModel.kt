package com.example.feature.home.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.presentation.extension.asStringResource
import com.example.feature.home.presentation.event.HomeEvent
import com.example.feature.home.presentation.event.HomeSideEffect
import com.example.feature.home.presentation.mapper.toPresentation
import com.example.feature.home.presentation.state.HomeState
import com.example.feature.home.presentation.state.TimeFilter
import com.example.feature.home.presentation.util.toDate
import com.example.feauture.home.domain.usecase.GetGenreListUseCase
import com.example.feauture.home.domain.usecase.GetMovieListUseCase
import com.example.feauture.home.domain.usecase.GetPopularMoviesUseCase
import com.example.feauture.home.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,

    ) :
    ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<HomeSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()


    init {
        event(HomeEvent.LoadMovies)
        event(HomeEvent.LoadGenres)
        event(HomeEvent.LoadUpcomingMovies)
        event(HomeEvent.LoadPopularMovies)

    }

    fun event(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadMovies -> {
                loadMovies()
            }

            HomeEvent.LoadGenres -> loadGenres()

            is HomeEvent.SearchMovies -> {
                _state.update { it.copy(search = event.movieTitle) }
                loadMovies()
            }

            is HomeEvent.FilterMoviesByGenre -> {
                _state.update { currentState ->
                    currentState.copy(
                        selectedGenreId = if (currentState.selectedGenreId == event.genreId) null else event.genreId,
                        genres = if (currentState.genres.firstOrNull { it.isSelected }?.id == event.genreId) currentState.genres.map {
                            it.copy(
                                isSelected = false
                            )
                        } else currentState.genres.map { it.copy(isSelected = it.id == event.genreId) })
                }
                loadMovies()
            }

            is HomeEvent.FilterMoviesByTime -> {
                _state.update { it.copy(selectedTimeFilter = event.timeFilter) }
                loadMovies()
            }

            HomeEvent.ClearFilterMoviesByTime -> {
                _state.update { it.copy(selectedTimeFilter = TimeFilter.NONE) }
                loadMovies()
            }

            HomeEvent.LoadUpcomingMovies -> loadUpcomingMovies()
            HomeEvent.LoadPopularMovies -> loadPopularMovies()
            HomeEvent.RefreshLayout -> {
                loadGenres()
                loadMovies()
                loadPopularMovies()
                loadUpcomingMovies()
            }
        }
    }


    private fun loadUpcomingMovies() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getUpcomingMoviesUseCase()) {
                is com.example.core.domain.util.Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))
                }

                is com.example.core.domain.util.Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            upcomingMovies = result.data.map { it.toPresentation() })
                    }

                }
            }
        }
    }

    private fun loadPopularMovies() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getPopularMoviesUseCase()) {
                is com.example.core.domain.util.Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))
                }

                is com.example.core.domain.util.Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            popularMovies = result.data.map { it.toPresentation() })
                    }

                }
            }
        }
    }

    // here we are loading movies.
    // using viewModelScope and also getMovieListUseCase, running coroutine on io thread.
    // getMovieListUseCase is invoked and collected
    // in constructor passing _state.values for filtering.
    // because getMovieListUseCase returns Flow<Resource<List<Movie>>> we are using .collect{} to continuously receive  updates.
    // using Resource which is sealed class so we must write every case.
    private fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getMovieListUseCase.invoke(
                endTime = _state.value.selectedTimeFilter.toDate().second,
                genreId = _state.value.selectedGenreId,
                search = _state.value.search,
                startTime = _state.value.selectedTimeFilter.toDate().first
            ).collect { result ->
                when (result) {
                    is com.example.core.domain.util.Resource.Error -> {  // stops loading
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is com.example.core.domain.util.Resource.Success -> {
                        val movies = result.data.map { movie ->
                            movie.toPresentation()
                                .copy(  // Converts each movie into a UI model (toUi()) and keeps its duration.
                                    duration = movie.duration
                                )
                        }
                        _state.update {
                            it.copy(   // Stops loading (isLoading = false).
                                isLoading = false,
                                movies = movies  //Updates the state with: fetched movie list
                            )
                        }
                    }
                }
            }
        }
    }

    // here we are loading genres.
    // using viewModelScope and also getGenreListUseCase, running coroutine on IO thread.
    // getGenreListUseCase is invoked and collected
    // because getGenreListUseCase returns Flow<Resource<List<GenreListResponse>>> we are using .collect{} to continuously receive  updates.
    // using Resource which is sealed class so we must write every case.
    private fun loadGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            getGenreListUseCase.invoke().collect { result ->
                when (result) {
                    is com.example.core.domain.util.Resource.Error -> {
                        _state.update { it.copy(isLoading = false) } // stops loading
                        _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))

                    }

                    is com.example.core.domain.util.Resource.Success -> {
                        val genres = result.data.map { genre ->
                            genre.toPresentation()  // Converts each genre into a UI model (toUi()) and keeps its duration.
                        } // handle null safety
                        _state.update {
                            it.copy(
                                isLoading = false, // updating loading state
                                genres = genres   // updating a state with fetched genre
                            )
                        }
                    }
                }
            }
        }
    }
}

