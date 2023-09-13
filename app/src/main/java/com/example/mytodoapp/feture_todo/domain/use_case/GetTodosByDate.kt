package com.example.mytodoapp.feture_todo.domain.use_case

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository
import com.example.mytodoapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodosByDate(
    private val repository: TodoRepository
) {
    operator fun invoke(
        minDate: Long,
        maxDate: Long,
        name: String = "",
        showCompleted: Boolean = true
    ): Flow<Resource<List<Todo>>> {
            return repository.getTodosByDate(minDate, maxDate, name).map { resource ->
            when (resource) {
                is Resource.Loading -> resource
                is Resource.Error -> resource
                is Resource.Success -> {
                    if (showCompleted) {
                        Resource.Success(resource.data?.sortedBy { it.startTime })
                    } else {
                        val filteredData = resource.data?.filter { !it.done }?.sortedBy { it.startTime }
                        Resource.Success(filteredData)
                    }
                }
            }
        }
    }
}