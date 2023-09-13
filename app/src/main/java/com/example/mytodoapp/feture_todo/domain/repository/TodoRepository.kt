package com.example.mytodoapp.feture_todo.domain.repository

import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.utils.LanguageOption
import com.example.mytodoapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getTodos(name: String): Flow<Resource<List<Todo>>>

    fun getTodosByDate(minDate: Long, maxDate: Long, name: String): Flow<Resource<List<Todo>>>

    fun getLanguage(): LanguageOption

    fun changeLanguage()
}