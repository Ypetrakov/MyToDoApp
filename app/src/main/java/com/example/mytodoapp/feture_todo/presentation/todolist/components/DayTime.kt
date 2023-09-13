package com.example.mytodoapp.feture_todo.presentation.todolist.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.feture_todo.presentation.util.Day


@Composable
fun DayTime(
    modifier: Modifier = Modifier,
    day: Day,
    isActive: Boolean = false,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(
                if (isActive) MaterialTheme.colorScheme.inversePrimary
                else MaterialTheme.colorScheme.primary
            )
            .width(45.dp)
            .height(70.dp)
            .padding(vertical = 10.dp)


    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            text = day.dayName,
            color = if (isActive) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = day.dayNumber,
            color = if (isActive) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun EmptyDayTime(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .width(45.dp)
            .height(70.dp)
            .padding(vertical = 10.dp)


    ) {

    }
}

