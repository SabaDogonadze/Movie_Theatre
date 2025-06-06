package com.example.feature.movie_detail.presentation.event

sealed interface MovieDetailEvent {
    data class GetMovieDetails(val movieId:Int) : MovieDetailEvent
    data class ScreeningItemClicked(val movieId:Int,val moviePrice:Double): MovieDetailEvent
    data class OnChangedScreeningChooser(val number:Int): MovieDetailEvent
}