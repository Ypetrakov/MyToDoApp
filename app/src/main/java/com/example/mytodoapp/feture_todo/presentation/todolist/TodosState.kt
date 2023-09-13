package com.example.mytodoapp.feture_todo.presentation.todolist

import com.example.mytodoapp.feture_todo.domain.model.Todo

data class TodosState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean  = true,
    val message: String? = null
)
