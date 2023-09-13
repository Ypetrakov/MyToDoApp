package com.example.mytodoapp.feture_todo.presentation.todo_creation.util

import java.time.LocalDateTime

object TodoValidator {
    fun validate(name: String, startDate: LocalDateTime, endTime: LocalDateTime): ValidateResult {
        if (name.isEmpty()) {
            return ValidateResult.Error("Your title cannot be empty")
        }
        if(name.isBlank()) {
            return ValidateResult.Error("Your title cannot be blank")
        }
        if (endTime < startDate) {
            return ValidateResult.Error("End Time should be larger than Start Time")
        }

        if (endTime < LocalDateTime.now()) {
            return ValidateResult.Error("Your end time already expired")
        }
        return ValidateResult.Success
    }
}

sealed class ValidateResult() {
    data class Error(val message: String): ValidateResult()
    object Success: ValidateResult()
}