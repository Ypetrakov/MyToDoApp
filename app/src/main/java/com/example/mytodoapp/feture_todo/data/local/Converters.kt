package com.example.mytodoapp.feture_todo.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.mytodoapp.feture_todo.data.util.JsonParser
import com.example.mytodoapp.feture_todo.domain.model.Work
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromWorksToJson(works: List<Work>): String{
        return jsonParser.toJson(
            works,
            object : TypeToken<ArrayList<Work>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromJsonToList(json: String): List<Work> {
        return jsonParser.fromJson<ArrayList<Work>>(
            json,
            object : TypeToken<ArrayList<Work>>(){}.type
        ) ?: emptyList()
    }
}