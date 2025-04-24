package com.example.feature.movie_detail.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val description: String,
    val duration: Int,
    val ageRestriction: String,
    val movieImgUrl: String,
    val director: String,
    val imdbRating: Double,
    val youtubeUrl: String,
    val genres: List<GenreDto>,
    val actors: List<ActorDto>,
    val screenings: List<ScreeningDto>,
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String,
)

@Serializable
data class ActorDto(
    val id: Int,
    val name: String,
    val actorImgUrl: String,
)

@Serializable
data class ScreeningDto(
    val id: Int,
    val screeningTime: String,
    val screeningPrice: Double,
    val iconUrl: String,
)
