package com.example.mytodoapp.feture_todo.presentation.preferences

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mytodoapp.feture_todo.domain.use_case.TodoUseCase
import com.example.mytodoapp.ui.MainActivity
import com.example.mytodoapp.utils.LanguageOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    val useCase: TodoUseCase
) : ViewModel() {

    var selected by mutableStateOf(false)
        private set

    init {
        changeSelected()
    }

    fun changeSelected() {
        selected = when (useCase.getLanguage()){
            LanguageOption.English -> true
            LanguageOption.Ukrainian -> false
        }
    }

    fun toggleSelected() {
        useCase.changeLanguage()
        changeSelected()
    }

}