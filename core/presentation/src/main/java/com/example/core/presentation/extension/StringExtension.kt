package com.example.core.presentation.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toFormattedDateTime(): String {
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return dateTime.format(timeFormatter)
}