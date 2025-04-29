package com.example.feature.movie_detail.presentation.extension

import com.example.feature.movie_detail.presentation.model.ScreeningPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



fun List<ScreeningPresenter>.filterByDay(day: Int): List<ScreeningPresenter> {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    return this
        .map { screening -> screening to LocalDateTime.parse(screening.screeningTime, formatter) }
        .filter { (_, dt) -> dt.dayOfMonth == day }
        .sortedBy { (_, dt) -> dt.toLocalTime() }
        .map { (screening, _) -> screening }
}