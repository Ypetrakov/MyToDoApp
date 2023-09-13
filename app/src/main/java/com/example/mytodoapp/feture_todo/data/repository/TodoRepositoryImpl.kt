package com.example.mytodoapp.feture_todo.data.repository

import com.example.mytodoapp.feture_todo.data.local.PreferencesManager
import com.example.mytodoapp.feture_todo.data.local.TodoDatabase
import com.example.mytodoapp.feture_todo.data.mapper.toTodo
import com.example.mytodoapp.feture_todo.data.mapper.toTodoEntity
import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository
import com.example.mytodoapp.utils.LANGUAGE
import com.example.mytodoapp.utils.LanguageOption
import com.example.mytodoapp.utils.Resource
import com.example.mytodoapp.utils.convertStringToLanguageOption
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    todoDatabase: TodoDatabase,
    private val preferencesManager: PreferencesManager,
    private val ioDispatcher: CoroutineDispatcher
) : TodoRepository {
    private val dao = todoDatabase.todosDao

    override suspend fun insertTodo(todo: Todo) {
        withContext(ioDispatcher) {
            dao.insertTodo(todo.toTodoEntity())
        }
    }

    override suspend fun deleteTodo(todo: Todo) {
        withContext(ioDispatcher) {
            dao.deleteTodo(todo.toTodoEntity())
        }
    }

    override fun getTodos(name: String): Flow<Resource<List<Todo>>> = flow {
        emit(Resource.Loading())
        val todos = dao.getTodos(name).map { it.toTodo() }
        emit(Resource.Success(data = todos))
    }

    override fun getTodosByDate(
        minDate: Long,
        maxDate: Long,
        name: String
    ): Flow<Resource<List<Todo>>> = flow {
        emit(Resource.Loading())
        val todos = dao.getTodosByDate(minDate, maxDate, name).map { it.toTodo() }
        emit(Resource.Success(data = todos))
    }

    override fun getLanguage(): LanguageOption {
        return convertStringToLanguageOption(
            preferencesManager.getDate(
                LANGUAGE,
                LanguageOption.English.textRepresentation
            )
        )
    }

    override fun changeLanguage() {
        val currentLanguage = getLanguage()
        preferencesManager.saveData(
            key = LANGUAGE,
            value = when (currentLanguage) {
                LanguageOption.Ukrainian -> LanguageOption.English.textRepresentation
                LanguageOption.English -> LanguageOption.Ukrainian.textRepresentation
            }
        )
    }


}