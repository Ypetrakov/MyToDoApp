package com.example.mytodoapp.feture_todo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mytodoapp.feture_todo.domain.model.Work
import org.jetbrains.annotations.NotNull


@Entity(tableName = "Todos")
data class TodoEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val worksList: List<Work>,
    val startTime: Long,
    val endTime: Long,
    val done: Boolean
)

