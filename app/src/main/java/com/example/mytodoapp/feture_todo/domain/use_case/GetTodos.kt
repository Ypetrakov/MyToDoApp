package com.example.mytodoapp.feture_todo.domain.use_case

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository
import com.example.mytodoapp.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetTodos (
    private val repository: TodoRepository
) {
    operator fun invoke(name: String): Flow<Resource<List<Todo>>> {
        return repository.getTodos(name)
    }
}