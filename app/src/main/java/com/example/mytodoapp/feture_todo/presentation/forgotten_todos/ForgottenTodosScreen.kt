package com.example.mytodoapp.feture_todo.presentation.forgotten_todos

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytodoapp.NavGraphs
import com.example.mytodoapp.feture_todo.presentation.forgotten_todos.components.TodoItemForgotten
import com.example.mytodoapp.startAppDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.utils.allDestinations
import com.ramcosta.composedestinations.utils.startDestination
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun ForgottenTodosScreen(
    drawerState: DrawerState,
    forgottenTodosViewModel: ForgottenTodosViewModel = hiltViewModel(),

    ) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                title = { Text("ForgottenTodos") }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                items = forgottenTodosViewModel.todos.todos,
                key = { todo -> todo.id!! }
            ) { todo ->
                TodoItemForgotten(
                    modifier = Modifier.animateItemPlacement(),
                    todo = todo,
                    onResetTodoClicked = {},
                    onDeleteTodoClicked = forgottenTodosViewModel::removeTodo
                )
            }
        }
    }
}