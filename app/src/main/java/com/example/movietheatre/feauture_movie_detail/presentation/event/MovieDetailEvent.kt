package com.example.movietheatre.feauture_movie_detail.presentation.event

sealed class MovieDetailEvent {
    data class GetMovieDetails(val movieId:Int) : MovieDetailEvent()
    data class ScreeningItemClicked(val movieId:Int,val moviePrice:Double):MovieDetailEvent()
}