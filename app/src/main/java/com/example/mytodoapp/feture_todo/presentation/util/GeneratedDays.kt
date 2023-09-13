package com.example.mytodoapp.feture_todo.presentation.util

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import java.time.LocalDate

object GeneratedDays {

    var days: List<Day> = emptyList()

    init {
        generateDays(60)
    }

    fun generateDays(numberOfDays: Int) {
        val today = LocalDate.now()
        val newDays: MutableList<Day> = mutableListOf()
        for(i in 0..numberOfDays) {
            val currentDay = today.plusDays(i.toLong())
            newDays.add(
                Day(
                    day = currentDay,
                    dayName = currentDay.dayOfWeek.name.substring(0..2).lowercase().replaceFirstChar { it.uppercase() },
                    dayNumber = currentDay.dayOfMonth.toString(),
                    month = currentDay.month.name.lowercase().replaceFirstChar { it.uppercase() }
                )
            )
        }
        days = newDays
    }
}