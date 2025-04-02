package com.example.movietheatre.feauture_movie_detail.data.remote.mapper

import com.example.movietheatre.feauture_movie_detail.data.remote.model.ActorDto
import com.example.movietheatre.feauture_movie_detail.data.remote.model.GenreDto
import com.example.movietheatre.feauture_movie_detail.data.remote.model.MovieDetailDto
import com.example.movietheatre.feauture_movie_detail.data.remote.model.ScreeningDto
import com.example.movietheatre.feauture_movie_detail.domain.model.Actor
import com.example.movietheatre.feauture_movie_detail.domain.model.Genre
import com.example.movietheatre.feauture_movie_detail.domain.model.MovieDetail
import com.example.movietheatre.feauture_movie_detail.domain.model.Screening

fun MovieDetailDto.toDomain():MovieDetail{
    return MovieDetail(
        id = id,
        title = title,
        description = description,
        duration = duration,
        ageRestriction = ageRestriction,
        movieImgUrl = movieImgUrl,
        director = director,
        imdbRating = imdbRating,
        youtubeUrl = youtubeUrl,
        genres = genres.map { it.toDomain() },
        actors = actors.map { it.toDomain() },
        screenings = screenings.map { it.toDomain() }
    )
}

fun GenreDto.toDomain():Genre{
    return Genre(id = id, name =name)
}

fun ActorDto.toDomain():Actor{
    return Actor(id = id, name = name, actorImgUrl = actorImgUrl)
}

fun ScreeningDto.toDomain():Screening{
    return Screening(id = id, screeningTime = screeningTime, screeningPrice = screeningPrice)
}