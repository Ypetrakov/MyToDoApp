package com.example.mytodoapp.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.mytodoapp.R
import com.example.mytodoapp.destinations.TodoListScreenDestination
import com.example.mytodoapp.destinations.ForgottenTodosScreenDestination
import com.example.mytodoapp.destinations.PreferencesScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class Destinations(
    val direction: DirectionDestinationSpec,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    TodoList(TodoListScreenDestination,  R.string.todo_dairy, Icons.Default.ListAlt),
    ForgottenTodo(ForgottenTodosScreenDestination, R.string.forgotten_todo, Icons.Default.FormatListNumbered),
    Preferences(PreferencesScreenDestination, R.string.settings, Icons.Default.Settings)

}