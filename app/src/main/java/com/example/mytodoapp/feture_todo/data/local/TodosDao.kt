package com.example.mytodoapp.feture_todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytodoapp.feture_todo.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("Select * FROM Todos WHERE endTime >= :minDate AND startTime <= :maxDate AND name LIKE '%' || :name || '%'")
    suspend fun getTodosByDate(minDate: Long, maxDate: Long, name: String): List<TodoEntity>

    @Query("Select * From Todos WHERE name LIKE '%' || :name || '%' ")
    suspend fun getTodos(name: String): List<TodoEntity>


}