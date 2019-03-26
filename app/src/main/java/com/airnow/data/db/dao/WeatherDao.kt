package com.airnow.data.db.dao

import androidx.room.*
import com.airnow.data.model.Weather
import com.airnow.data.db.AppDatabase.Companion.WEATHER_TABLE

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weatherList: List<Weather>)

    @Delete
    fun deleteWeather(weather: Weather)

    @Query("DELETE FROM $WEATHER_TABLE")
    fun deleteAll()

    @Query("SELECT * FROM $WEATHER_TABLE")
    fun getAllWeatherList():List<Weather>

}