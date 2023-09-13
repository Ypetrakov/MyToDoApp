package com.example.mytodoapp.feture_todo.presentation.todolist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.presentation.util.toLocalDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone


val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d.MM", Locale.ENGLISH)
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

@Composable
fun TodoItem(
    todo: Todo,
    onTodoBoxClicked: (Todo) -> Unit,
    onWorkBoxClicked: (Todo, Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
    ) {
        Column(
            Modifier.weight(1f, false)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    onTodoBoxClicked(todo)
                }
            ) {
                Checkbox(checked = todo.done, onCheckedChange = { onTodoBoxClicked(todo) })
                Text(
                    text = todo.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            todo.worksList.forEachIndexed { index, work ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(PaddingValues(start = 35.dp))
                        .clickable {
                            onWorkBoxClicked(todo, index)
                        }
                ) {
                    Checkbox(
                        checked = work.isDone,
                        onCheckedChange = { onWorkBoxClicked(todo, index) })
                    Text(
                        text = work.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = "Up to",
                style = MaterialTheme.typography.labelMedium,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MaterialTheme.colorScheme.secondary)

            ) {
                Text(
                    text = todo.endTime.toLocalDateTime().format(dateFormatter),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary

                )
                Text(
                    text = todo.endTime.toLocalDateTime().format(timeFormatter),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary

                )
            }
        }

    }
}