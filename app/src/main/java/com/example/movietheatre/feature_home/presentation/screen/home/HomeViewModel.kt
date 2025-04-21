package com.example.movietheatre.feature_home.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feature_home.domain.usecase.GetGenreListUseCase
import com.example.movietheatre.feature_home.domain.usecase.GetMovieListUseCase
import com.example.movietheatre.feature_home.domain.usecase.GetUpcomingMoviesUseCase
import com.example.movietheatre.feature_home.presentation.event.HomeEvent
import com.example.movietheatre.feature_home.presentation.event.HomeSideEffect
import com.example.movietheatre.feature_home.presentation.mapper.toPresentation
import com.example.movietheatre.feature_home.presentation.state.HomeState
import com.example.movietheatre.feature_home.presentation.state.TimeFilter
import com.example.movietheatre.feature_home.presentation.util.toDate
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
) :
    ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<HomeSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()


    init {
        event(HomeEvent.LoadUpcomingMovies)
    }

    //handling events
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
        }
    }


    private fun loadUpcomingMovies() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getUpcomingMoviesUseCase()) {
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            upcomingMovies = result.data.map { it.toPresentation() })
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
                    is Resource.Error -> {  // stops loading
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is Resource.Success -> {
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
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false) } // stops loading
                        _uiEvents.emit(HomeSideEffect.ShowError(result.error.asStringResource()))

                    }

                    is Resource.Success -> {
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

