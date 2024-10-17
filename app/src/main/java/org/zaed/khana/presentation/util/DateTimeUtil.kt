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
fun Long.formatEpochSecondsToDateTime(): String {
    val dateTime = Instant.fromEpochSeconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = dateTime.dayOfMonth
    val year = dateTime.year
    val hour = dateTime.hour % 12
    val formattedHour = (if (hour == 0) 12 else hour).toString().padStart(2, '0')
    val minute = dateTime.minute.toString().padStart(2, '0')
    val amPm = if (dateTime.hour < 12) "am" else "pm"

    return "$month $day, $year, $formattedHour:$minute $amPm"
}
