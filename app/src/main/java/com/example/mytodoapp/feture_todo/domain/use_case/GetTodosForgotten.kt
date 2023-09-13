package com.example.mytodoapp.feture_todo.domain.use_case

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository
import com.example.mytodoapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class GetTodosForgotten(
    private val repository: TodoRepository
) {
    operator fun invoke(
        name: String = "",
    ): Flow<Resource<List<Todo>>> {
        return repository.getTodos(name).map { resource ->
            when (resource) {
                is Resource.Loading -> resource
                is Resource.Error -> resource
                is Resource.Success -> {
                    val filteredData = resource.data?.filter {
                        it.endTime < LocalDateTime.now().toEpochSecond(
                            ZoneId.systemDefault().rules.getOffset(
                                Instant.now()
                            )
                        ) && !it.done
                    }
                    Resource.Success(filteredData)
                }
            }
        }
    }
}