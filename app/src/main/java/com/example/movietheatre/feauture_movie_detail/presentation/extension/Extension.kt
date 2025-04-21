package com.example.movietheatre.feauture_movie_detail.presentation.extension

import com.example.movietheatre.feauture_movie_detail.presentation.model.ScreeningPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toFormattedDateTime(): String {
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return dateTime.format(timeFormatter)
}

fun List<ScreeningPresenter>.filterByDay(day: Int): List<ScreeningPresenter> {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    return this
        .map { screening -> screening to LocalDateTime.parse(screening.screeningTime, formatter) }
        .filter { (_, dt) -> dt.dayOfMonth == day }
        .sortedBy { (_, dt) -> dt.toLocalTime() }
        .map { (screening, _) -> screening }
}