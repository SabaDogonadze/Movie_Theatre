package com.example.movietheatre.feauture_movie_detail.presentation.mapper

import com.example.movietheatre.feauture_movie_detail.domain.model.Actor
import com.example.movietheatre.feauture_movie_detail.domain.model.Genre
import com.example.movietheatre.feauture_movie_detail.domain.model.MovieDetail
import com.example.movietheatre.feauture_movie_detail.domain.model.Screening
import com.example.movietheatre.feauture_movie_detail.presentation.model.ActorPresenter
import com.example.movietheatre.feauture_movie_detail.presentation.model.GenrePresenter
import com.example.movietheatre.feauture_movie_detail.presentation.model.MovieDetailPresenter
import com.example.movietheatre.feauture_movie_detail.presentation.model.ScreeningPresenter

fun MovieDetail.toPresenter(): MovieDetailPresenter {
    return MovieDetailPresenter(
        id = id,
        title = title,
        description = description,
        duration = duration,
        ageRestriction = ageRestriction,
        movieImgUrl = movieImgUrl,
        director = director,
        imdbRating = imdbRating,
        youtubeUrl = youtubeUrl, // bolos aq ar mchirdeba
        genres = genres.map { it.toPresenter() },
        actors = actors.map { it.toPresenter() },
        screenings = screenings.map { it.toPresenter() }
    )
}

fun Genre.toPresenter(): GenrePresenter {
    return GenrePresenter(id = id, name =name)
}

fun Actor.toPresenter(): ActorPresenter {
    return ActorPresenter(id = id, name = name, actorImgUrl = actorImgUrl)
}

fun Screening.toPresenter(): ScreeningPresenter {
    return ScreeningPresenter(id = id, screeningTime = screeningTime, screeningPrice = screeningPrice)
}