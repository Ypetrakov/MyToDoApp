package com.example.mytodoapp.feture_todo.presentation.forgotten_todos

import com.example.mytodoapp.feture_todo.domain.model.Todo

data class ForgottenTodosState (
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean  = false,
    val message: String? = null
)