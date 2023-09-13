package com.example.mytodoapp.feture_todo.domain.use_case

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository

class DeleteTodo (
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }
}