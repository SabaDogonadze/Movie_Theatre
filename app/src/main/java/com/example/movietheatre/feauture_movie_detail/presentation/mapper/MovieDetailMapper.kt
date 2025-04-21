package com.example.movietheatre.feauture_movie_detail.presentation.mapper

import com.example.movietheatre.feauture_movie_detail.domain.model.Actor
import com.example.movietheatre.feauture_movie_detail.domain.model.Genre
import com.example.movietheatre.feauture_movie_detail.domain.model.MovieDetail
import com.example.movietheatre.feauture_movie_detail.domain.model.Screening
import com.example.movietheatre.feauture_movie_detail.presentation.model.ActorPresenter
import com.example.movietheatre.feauture_movie_detail.presentation.model.GenrePresenter
import com.example.movietheatre.feauture_movie_detail.presentation.model.MovieDetailPresenter
import com.example.movietheatre.feauture_movie_detail.presentation.model.ScreeningDateChooser
import com.example.movietheatre.feauture_movie_detail.presentation.model.ScreeningPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
        screenings = screenings.map { it.toPresenter() },
        screeningsChooser = screenings.toScreeningChoose()
    )
}

fun List<Screening>.toScreeningChoose(): List<ScreeningDateChooser> {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    val dayWeekPairs = this
        .map { screening ->
            val dateTime = LocalDateTime.parse(screening.screeningTime, formatter)
            val day = dateTime.dayOfMonth
            val weekday = dateTime.format(
                DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)
            )
            day to weekday
        }
        .distinctBy { it.first }
        .sortedBy { it.first }
    return dayWeekPairs.mapIndexed { index, (day, weekday) ->
        ScreeningDateChooser(
            number = day,
            title = weekday,
            isSelected = (index == 0)
        )
    }
}

fun Genre.toPresenter(): GenrePresenter {
    return GenrePresenter(id = id, name = name)
}

fun Actor.toPresenter(): ActorPresenter {
    return ActorPresenter(id = id, name = name, actorImgUrl = actorImgUrl)
}

fun Screening.toPresenter(): ScreeningPresenter {
    return ScreeningPresenter(
        id = id,
        screeningTime = screeningTime,
        screeningPrice = screeningPrice,
        iconUrl = iconUrl
    )
}