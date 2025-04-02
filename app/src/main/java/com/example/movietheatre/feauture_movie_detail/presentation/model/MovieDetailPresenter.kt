package com.example.movietheatre.feauture_movie_detail.presentation.model

data class MovieDetailPresenter(
    val id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val ageRestriction: String,
    val movieImgUrl: String,
    val director: String,
    val imdbRating: Double,
    val youtubeUrl: String,
    val genres: List<GenrePresenter>,
    val actors: List<ActorPresenter>,
    val screenings: List<ScreeningPresenter>
)


data class GenrePresenter(
    val id: Int,
    val name: String
)

data class ActorPresenter(
    val id: Int,
    val name: String,
    val actorImgUrl: String
)

data class ScreeningPresenter(
    val id: Int,
    val screeningTime: String,
    val screeningPrice: Double
)
