package com.example.mytodoapp.feture_todo.presentation.todolist

import com.example.mytodoapp.feture_todo.domain.model.Todo

sealed class TodoListEvent {
    data class InsertTodo(val todo: Todo) : TodoListEvent()
    data class DeleteTodo(val todo: Todo) : TodoListEvent()
}
