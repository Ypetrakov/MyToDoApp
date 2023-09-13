package com.example.mytodoapp.feature_auth.presentation.sign_in


data class SignInResult(
    val data: UserData?,
    val errorMessage: String? = null
)

data class  UserData (
    val userId: String,
    val username: String?,
    val profilePicture: String?
)
