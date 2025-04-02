package com.example.movietheatre.feauture_movie_detail.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val ageRestriction: String,
    val movieImgUrl: String,
    val director: String,
    val imdbRating: Double,
    val youtubeUrl: String,
    val genres: List<Genre>,
    val actors: List<Actor>,
    val screenings: List<Screening>
)


data class Genre(
    val id: Int,
    val name: String
)

data class Actor(
    val id: Int,
    val name: String,
    val actorImgUrl: String
)

data class Screening(
    val id: Int,
    val screeningTime: String,
    val screeningPrice: Double
)
