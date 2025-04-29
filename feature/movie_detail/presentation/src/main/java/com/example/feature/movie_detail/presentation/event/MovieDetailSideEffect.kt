package com.example.feature.movie_detail.presentation.event

sealed class MovieDetailSideEffect {
    data class ShowError(val message: Int) : MovieDetailSideEffect()
    data class NavigateToBookingFragment(val movieId:Int,val moviePrice:Float) : MovieDetailSideEffect()
}