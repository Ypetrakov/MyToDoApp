package com.example.mytodoapp.feture_todo.presentation.forgotten_todos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.use_case.TodoUseCase
import com.example.mytodoapp.feture_todo.presentation.todo_creation.UiEvent
import com.example.mytodoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgottenTodosViewModel @Inject constructor(
    private val useCase: TodoUseCase
) : ViewModel() {

    private var getTodosJob: Job? = null

    var todos by mutableStateOf(ForgottenTodosState())
        private set

    private var _sharedFlow: SharedFlow<UiEvent> = MutableSharedFlow()


    init {
        getForgottenTodos()
    }

    private fun getForgottenTodos() {
        getTodosJob?.cancel()
        getTodosJob = viewModelScope.launch {
            useCase.getTodosForgotten().collectLatest { result ->
                todos = when (result) {
                    is Resource.Success -> {
                        ForgottenTodosState(todos = result.data!!)
                    }

                    is Resource.Loading -> {
                        todos.copy(isLoading = true)
                    }

                    is Resource.Error -> {
                        ForgottenTodosState(message = result.message)
                    }
                }
            }
        }
    }

    fun removeTodo(todo: Todo) {
        viewModelScope.launch {
            useCase.deleteTodo(todo)
            getForgottenTodos()
        }
    }
}