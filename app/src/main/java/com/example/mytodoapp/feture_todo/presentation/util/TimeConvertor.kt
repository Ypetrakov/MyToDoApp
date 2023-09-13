package com.example.mytodoapp.feture_todo.presentation.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.TimeZone


fun LocalDateTime.changeLocalDate(date: LocalDate): LocalDateTime =
    this
        .withYear(date.year)
        .withMonth(date.monthValue)
        .withDayOfMonth(date.dayOfMonth)

fun LocalDateTime.changeLocalTime(time: LocalTime): LocalDateTime =
    this
        .withHour(time.hour)
        .withMinute(time.minute)


fun LocalDate.getStartOfDayMillis(): Long {
    return this.atStartOfDay().toEpochSecond(OffsetDateTime.now().offset)
}

fun LocalDate.getEndOfDayMillis(): Long {
    return this.atStartOfDay().plusDays(1).minusSeconds(1)
        .toEpochSecond(OffsetDateTime.now().offset)
}

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this),
        TimeZone.getDefault().toZoneId()
    )
