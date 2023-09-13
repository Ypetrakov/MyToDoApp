package com.example.mytodoapp.feture_todo.domain.model

data class Work(
    val id: Int,
    val name: String,
    var isDone: Boolean = false
) {

}
