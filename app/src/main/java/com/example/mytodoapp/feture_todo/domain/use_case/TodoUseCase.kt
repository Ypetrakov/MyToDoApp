package com.example.mytodoapp.feture_todo.domain.use_case

data class TodoUseCase(
    val deleteTodo: DeleteTodo,
    val getTodosByDate: GetTodosByDate,
    val getTodos: GetTodos,
    val insertTodo: InsertTodo,
    val getTodosForgotten: GetTodosForgotten,
    val getLanguage: GetLanguage,
    val changeLanguage: ChangeLanguage
)