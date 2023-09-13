package com.example.mytodoapp.feture_todo.presentation.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

data class Day(
    val dayNumber: String,
    val month: String,
    val dayName: String,
    val day: LocalDate
)
