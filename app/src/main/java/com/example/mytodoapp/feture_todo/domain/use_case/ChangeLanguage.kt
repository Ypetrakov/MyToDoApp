package com.example.mytodoapp.feture_todo.domain.use_case

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository

class ChangeLanguage (
    private val repository: TodoRepository
) {
    operator fun invoke() {
        repository.changeLanguage()
    }
}