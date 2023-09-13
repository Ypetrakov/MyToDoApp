package com.example.mytodoapp.utils

val LANGUAGE = "Language"

enum class LanguageOption(val textRepresentation: String) {
    Ukrainian("Ukrainian"),
    English("English")
}

fun convertStringToLanguageOption(string: String): LanguageOption =
    when (string) {
        LanguageOption.Ukrainian.textRepresentation -> LanguageOption.Ukrainian
        else -> LanguageOption.English
    }
