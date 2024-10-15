package org.zaed.khana.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.formatEpochSecondsToDate(): String {
    val dateTime = Instant.fromEpochSeconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = dateTime.dayOfMonth
    val year = dateTime.year
    return "$month $day, $year"
}