package com.example.mytodoapp.feture_todo.domain.model

import java.util.Date

data class Todo(
    val id: Int? = null,
    val name: String,
    val worksList: List<Work>,
    val startTime: Long,
    val endTime: Long,
    val done: Boolean = false
)
