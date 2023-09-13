package com.example.mytodoapp.feture_todo.data.mapper

import com.example.mytodoapp.feture_todo.data.local.entity.TodoEntity
import com.example.mytodoapp.feture_todo.domain.model.Todo

fun TodoEntity.toTodo(): Todo {
    return Todo(
        id = id,
        name = name,
        worksList = worksList,
        startTime = startTime,
        endTime = endTime,
        done = done
    )
}

fun Todo.toTodoEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        name = name,
        worksList = worksList,
        startTime = startTime,
        endTime = endTime,
        done = done
    )
}