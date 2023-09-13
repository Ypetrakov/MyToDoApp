package com.example.mytodoapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mytodoapp.feture_todo.data.local.Converters
import com.example.mytodoapp.feture_todo.data.local.PreferencesManager
import com.example.mytodoapp.feture_todo.data.local.TodoDatabase
import com.example.mytodoapp.feture_todo.data.repository.TodoRepositoryImpl
import com.example.mytodoapp.feture_todo.data.util.GsonParser
import com.example.mytodoapp.feture_todo.domain.repository.TodoRepository
import com.example.mytodoapp.feture_todo.domain.use_case.ChangeLanguage
import com.example.mytodoapp.feture_todo.domain.use_case.DeleteTodo
import com.example.mytodoapp.feture_todo.domain.use_case.GetLanguage
import com.example.mytodoapp.feture_todo.domain.use_case.GetTodos
import com.example.mytodoapp.feture_todo.domain.use_case.GetTodosByDate
import com.example.mytodoapp.feture_todo.domain.use_case.GetTodosForgotten
import com.example.mytodoapp.feture_todo.domain.use_case.InsertTodo
import com.example.mytodoapp.feture_todo.domain.use_case.TodoUseCase
import com.example.mytodoapp.feture_todo.presentation.util.GeneratedDays
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun provideUseCase(
        repository: TodoRepository
    ): TodoUseCase {
        return TodoUseCase(
            deleteTodo = DeleteTodo(repository),
            insertTodo = InsertTodo(repository),
            getTodos = GetTodos(repository),
            getTodosByDate = GetTodosByDate(repository),
            getTodosForgotten = GetTodosForgotten(repository),
            getLanguage = GetLanguage(repository),
            changeLanguage = ChangeLanguage(repository)
        )
    }

    @Provides
    @Singleton
    fun provideDayOfWeek(): GeneratedDays = GeneratedDays

    @Provides
    @Singleton
    fun provideTodoRepository(
        db: TodoDatabase,
        preferencesManager: PreferencesManager,
        ioDispatcher: CoroutineDispatcher
    ): TodoRepository {
        return TodoRepositoryImpl(
            todoDatabase = db,
            preferencesManager = preferencesManager,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context) =
        PreferencesManager(context)
}