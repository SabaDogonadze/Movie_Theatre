package com.example.movietheatre.feauture_movie_detail.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feauture_movie_detail.domain.use_case.GetMovieDetailUseCase
import com.example.movietheatre.feauture_movie_detail.presentation.event.MovieDetailEvent
import com.example.movietheatre.feauture_movie_detail.presentation.event.MovieDetailSideEffect
import com.example.movietheatre.feauture_movie_detail.presentation.mapper.toPresenter
import com.example.movietheatre.feauture_movie_detail.presentation.state.MovieDetailState
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
class MovieDetailViewModel @Inject constructor(private val movieDetailUseCase: GetMovieDetailUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<MovieDetailSideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun event(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.GetMovieDetails -> loadDetailMovie(movieId = event.movieId)
            is MovieDetailEvent.ScreeningItemClicked -> {
                screeningItemClicked(event.movieId, event.moviePrice.toFloat())
            }

            is MovieDetailEvent.OnChangedScreeningChooser -> onChangedScreeningChooser(event.number)
        }
    }


    private fun onChangedScreeningChooser(number: Int) {
        _state.update { currentState ->
            val updatedChooser = currentState.detailedMovie.screeningsChooser.map { chooser ->
                chooser.copy(isSelected = chooser.number == number)
            }

            val updatedDetailedMovie = currentState.detailedMovie.copy(
                screeningsChooser = updatedChooser
            )

            currentState.copy(detailedMovie = updatedDetailedMovie)
        }
    }


    private fun loadDetailMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            movieDetailUseCase.invoke(movieId).collect { result ->
                when (result) {
                    is Resource.Error -> {  // stops loading
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        _uiEvents.emit(MovieDetailSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is Resource.Success -> {
                        val movieDetail = result.data.toPresenter()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                detailedMovie = movieDetail,
                                youtubeVideoUrl = movieDetail.youtubeUrl
                            )
                        }
                    }
                }
            }
        }
    }

    private fun screeningItemClicked(movieId: Int, moviePrice: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiEvents.emit(
                MovieDetailSideEffect.NavigateToBookingFragment(
                    movieId = movieId,
                    moviePrice = moviePrice
                )
            )
        }
    }
}