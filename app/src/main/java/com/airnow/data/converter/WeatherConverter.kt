package com.airnow.data.converter

import androidx.room.TypeConverter
import com.airnow.data.model.WeatherEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class WeatherConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<WeatherEntity> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<WeatherEntity>>() {

        }.getType()

        return gson.fromJson<List<WeatherEntity>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<WeatherEntity>): String {
        return gson.toJson(someObjects)
    }
}