package com.example.mytodoapp.feture_todo.presentation.todolist

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.use_case.TodoUseCase
import com.example.mytodoapp.feture_todo.presentation.util.Day
import com.example.mytodoapp.feture_todo.presentation.util.GeneratedDays
import com.example.mytodoapp.feture_todo.presentation.util.getEndOfDayMillis
import com.example.mytodoapp.feture_todo.presentation.util.getStartOfDayMillis
import com.example.mytodoapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val useCase: TodoUseCase,
    private val generatedDays: GeneratedDays
) : ViewModel() {

    val days: List<Day> = generatedDays.days

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.ENGLISH)

    private var getTodoJob: Job? = null

    private var selectedDay by mutableStateOf(days[0])


    val formattedSelectedDate: String by derivedStateOf {
        var formattedDate = selectedDay.day.format(formatter)
        formattedDate = when (selectedDay.day) {
            LocalDate.now() -> {
                "$formattedDate Today"
            }
            LocalDate.now().plusDays(1) -> {
                "$formattedDate Tomorrow"
            }
            else -> formattedDate
        }

        formattedDate
    }

    var showCompeted by mutableStateOf(true)
        private set

    fun toggleShowCompleted() {
        showCompeted = !showCompeted
        direction = 0
        getTodosByDate()
    }

    var direction: Int = 1
        private set


    var selectedPosition by mutableIntStateOf(0)
        private set

    var todos by mutableStateOf(TodosState())

    init {
        getTodosByDate()
    }

    fun updateSelectedDay(centerPosition: Int) {
        direction = if (centerPosition > selectedPosition) 1 else -1
        selectedDay = days[centerPosition]
        selectedPosition = centerPosition
        getTodosByDate()
    }

    fun toggleCheckbox(todo: Todo) {
        viewModelScope.launch {
            if (!todo.worksList.all { work -> work.isDone }) {
                return@launch
            }
            val toggledTodo = todo.copy(
                done = !todo.done
            )
            useCase.insertTodo(toggledTodo)
            direction = 0
            getTodosByDate()
        }
    }

    fun toggleWorkCheckbox(todo: Todo, workIndex: Int) {
        viewModelScope.launch {
            val newWorkList = todo.worksList.toMutableList()

            newWorkList[workIndex] =
                newWorkList[workIndex].copy(isDone = !newWorkList[workIndex].isDone)

            val toggledTodo = todo.copy(
                worksList = newWorkList,
                done = if (todo.worksList[workIndex].isDone and todo.done) false else todo.done
            )

            useCase.insertTodo(toggledTodo)
            direction = 0
            getTodosByDate()
        }
    }

    private fun getTodosByDate() {
        getTodoJob?.cancel()
        val startTime = selectedDay.day.getStartOfDayMillis()
        val endTime = selectedDay.day.getEndOfDayMillis()

        getTodoJob = viewModelScope.launch {
            useCase.getTodosByDate(startTime, endTime, "", showCompeted).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        todos = todos.copy(
                            todos = result.data!!,
                            isLoading = false,
                            message = null
                        )
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {

                    }
                }
            }
        }

    }
}