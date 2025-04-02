package com.example.movietheatre.feauture_movie_detail.presentation.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toFormattedDateTime(): String {
    val dateTime = LocalDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy h:mm a")
    return dateTime.format(formatter)
}