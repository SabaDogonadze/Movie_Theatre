package com.example.movietheatre.feauture_movie_detail.presentation.state

import com.example.movietheatre.feauture_movie_detail.presentation.model.MovieDetailPresenter

data class MovieDetailState(
    val isLoading:Boolean = false,
    val detailedMovie: MovieDetailPresenter = MovieDetailPresenter(
        id = 0,
        title = "",
        description = "",
        duration = 0,
        ageRestriction = "",
        movieImgUrl = "",
        director = "",
        imdbRating = 0.0,
        youtubeUrl = "",
        genres = listOf(),
        actors = listOf(),
        screenings = listOf()
    ),
    val youtubeVideoUrl: String = ""
)
