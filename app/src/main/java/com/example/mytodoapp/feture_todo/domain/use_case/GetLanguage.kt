package com.example.mytodoapp.feture_todo.domain.use_case

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository
import com.example.mytodoapp.utils.LanguageOption

class GetLanguage (
    private val repository: TodoRepository
) {
    operator fun invoke(): LanguageOption {
        return repository.getLanguage()
    }
}