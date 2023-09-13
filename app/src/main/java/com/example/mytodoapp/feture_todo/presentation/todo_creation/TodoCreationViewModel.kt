package com.example.mytodoapp.feture_todo.presentation.todo_creation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.feture_todo.domain.model.Todo
import com.example.mytodoapp.feture_todo.domain.model.Work
import com.example.mytodoapp.feture_todo.domain.use_case.TodoUseCase
import com.example.mytodoapp.feture_todo.presentation.todo_creation.util.ValidateResult
import com.example.mytodoapp.feture_todo.presentation.util.changeLocalDate
import com.example.mytodoapp.feture_todo.presentation.util.changeLocalTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class TodoCreationViewModel @Inject constructor(
    private val useCase: TodoUseCase
) : ViewModel() {

    private val timeFormatter = DateTimeFormatter.ofPattern("uuuu.MM.dd HH:mm")

    var title by mutableStateOf("")
        private set

    var startDate: LocalDateTime by mutableStateOf(LocalDate.now().atStartOfDay())
        private set

    val formattedStartDate = derivedStateOf {
        timeFormatter.format(startDate) ?: ""
    }

    var endDate: LocalDateTime by mutableStateOf(
        LocalDate.now().atStartOfDay().plusDays(1).minusSeconds(1)
    )
        private set

    val formattedEndDate = derivedStateOf {
        timeFormatter.format(endDate) ?: ""
    }

    var isEndTimeChanged by mutableStateOf(false)
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun changeSelectedDate(date: LocalDate) {
        startDate = startDate.changeLocalDate(date)
        if (!isEndTimeChanged) {
            endDate = startDate.toLocalDate().atStartOfDay().plusDays(1).minusSeconds(1)
        }
    }

    fun changeSelectedTime(time: LocalTime) {
        startDate = startDate.changeLocalTime(time)
    }

    fun changeEndDate(date: LocalDate) {
        isEndTimeChanged = true
        endDate = endDate.changeLocalDate(date)
    }

    fun changeEndTime(time: LocalTime) {
        isEndTimeChanged = true
        endDate = endDate.changeLocalTime(time)
    }

    private val _worksList = mutableStateListOf(Work(0, ""))
    val workList: List<Work> = _worksList

    fun changeTitle(newTitle: String) {
        title = newTitle
    }

    fun changeWorkByIndex(index: Int, newValue: String) {
        _worksList[index] = _worksList[index].copy(name = newValue)
    }

    fun addWork() {
        _worksList.add(Work(_worksList.size, ""))
    }

    fun saveTodo() {
        viewModelScope.launch {
            val works = _worksList.filter { it.name.isNotEmpty() }
            val todo = Todo(
                name = title,
                worksList = works,
                startTime = startDate.toEpochSecond(OffsetDateTime.now().offset),
                endTime = endDate.toEpochSecond(OffsetDateTime.now().offset)
            )
            useCase.insertTodo(todo)
        }
    }
}

sealed class UiEvent {
    data class SaveTodo(val result: ValidateResult) : UiEvent()
}

