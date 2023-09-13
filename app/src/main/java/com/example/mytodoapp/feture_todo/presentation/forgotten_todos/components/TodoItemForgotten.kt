package com.example.mytodoapp.feture_todo.presentation.forgotten_todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.presentation.util.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM", Locale.ENGLISH)
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)

@Composable
fun TodoItemForgotten(
    todo: Todo,
    modifier: Modifier = Modifier,
    onResetTodoClicked: (Todo) -> Unit,
    onDeleteTodoClicked: (Todo) -> Unit
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .width(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                Modifier.weight(1f, false)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(checked = todo.done, onCheckedChange = { })
                    Text(
                        text = todo.name,
                        style = MaterialTheme.typography.bodyLarge,

                        )
                }
                todo.worksList.forEachIndexed { index, work ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(PaddingValues(start = 35.dp))

                    ) {
                        Checkbox(
                            checked = work.isDone,
                            onCheckedChange = { })
                        Text(
                            text = work.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.labelMedium,
                )

                Column(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(MaterialTheme.colorScheme.secondary)

                ) {
                    Text(
                        text = todo.startTime.toLocalDateTime().format(dateFormatter),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondary

                    )
                    Text(
                        text = todo.startTime.toLocalDateTime().format(timeFormatter),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondary

                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "To",
                    style = MaterialTheme.typography.labelMedium,
                )

                Column(
                    modifier = Modifier
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
            IconButton(
                onClick = { onDeleteTodoClicked(todo) },
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }

        }
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .clickable { onResetTodoClicked(todo) },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Reset Todo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}


@Composable
@Preview
fun TodoItemForgottenPreview() {
    val todo = Todo(
        name = "Do smsdasdasdasdasd asdasth asdasdasd asdasdas",
        worksList = emptyList(),
        startTime = 1000000,
        endTime = 10102002
    )
    TodoItemForgotten(todo = todo, onResetTodoClicked = {}, onDeleteTodoClicked = {})
}


@Composable
@Preview
fun TodoItemForgottenPreviewSecond() {
    val todo = Todo(
        name = "Do",
        worksList = emptyList(),
        startTime = 1000000,
        endTime = 10102002
    )
    TodoItemForgotten(todo = todo, onResetTodoClicked = {}, onDeleteTodoClicked = {})
}