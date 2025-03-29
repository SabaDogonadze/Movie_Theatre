package com.example.movietheatre.feature_home.presentation.util

import com.example.movietheatre.feature_home.presentation.state.TimeFilter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


fun getTimeRangeForInterval(interval: String): Pair<LocalDateTime, LocalDateTime> {
    val today = LocalDate.now()
    return when (interval) {
        "11:00-15:00" -> Pair(
            LocalDateTime.of(today, LocalTime.of(11, 0)),
            LocalDateTime.of(today, LocalTime.of(15, 0))
        )

        "15:00-19:00" -> Pair(
            LocalDateTime.of(today, LocalTime.of(15, 0)),
            LocalDateTime.of(today, LocalTime.of(19, 0))
        )

        "19:00-00:00" -> Pair(
            LocalDateTime.of(today, LocalTime.of(19, 0)),
            LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT)
        )


        else -> throw IllegalArgumentException("Unknown interval: $interval")
    }
}

fun TimeFilter.toDate():Pair<LocalDateTime, LocalDateTime>{
    val today = LocalDate.now()
    return when(this){
        TimeFilter.NONE -> {
            Pair(today.atStartOfDay(),today.atTime(LocalTime.MAX))
        }
        TimeFilter.MORNING -> {
             Pair(
            LocalDateTime.of(today, LocalTime.of(9, 0)),
            LocalDateTime.of(today, LocalTime.of(14, 59))
            )
        }
        TimeFilter.AFTERNOON -> {
            Pair(
                LocalDateTime.of(today, LocalTime.of(15, 0)),
                LocalDateTime.of(today, LocalTime.of(18, 59))
            )
        }
        TimeFilter.NIGHT -> {
            Pair(
                LocalDateTime.of(today, LocalTime.of(19, 0)),
                LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT)
            )
        }
    }
}
