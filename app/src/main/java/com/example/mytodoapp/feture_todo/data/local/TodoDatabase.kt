package com.example.mytodoapp.feture_todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mytodoapp.feture_todo.data.local.entity.TodoEntity


@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todosDao: TodosDao

    companion object{
        const val DATABASE_NAME = "todos_db"
    }
}