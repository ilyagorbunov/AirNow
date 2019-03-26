package com.airnow.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.airnow.data.converter.WeatherConverter
import com.airnow.data.db.AppDatabase
import com.airnow.ui.adapter.diff.Diffable

@Entity(tableName = AppDatabase.WEATHER_TABLE)
data class Weather(

        @PrimaryKey
        var id: Int,

        var base: String,

        var cod: Int,

        var dt: Int,

        var name: String,

        @Embedded(prefix = "clouds_")
        var clouds: Clouds?,

        @Embedded(prefix = "coord_")
        var coord: Coord?,

        @Embedded(prefix = "main_")
        var main: Main?,

        @Embedded(prefix = "rain_")
        var rain: Rain?,

        @Embedded(prefix = "sys_")
        var sys: Sys?,

        @TypeConverters(WeatherConverter::class)
        var weather: MutableList<WeatherEntity>?,

        @Embedded(prefix = "wind_")
        var wind: Wind?
) : Diffable {
        override fun isSame(item: Any) = equals(item)

        override fun hasSameContentWith(item: Any) = equals(item)
}

data class Main(
    var humidity: Double,
    var pressure: Double,
    var temp: Double,
    var temp_max: Double,
    var temp_min: Double
)

data class Wind(
    var deg: Double,
    var speed: Double
)

data class WeatherEntity(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
)

data class Rain(
    var h: Int
)

data class Sys(
    var country: String,
    var id: Int,
    var message: Double,
    var sunrise: Int,
    var sunset: Int,
    var type: Int
)

data class Clouds(
    var all: Int
)

data class Coord(
    var lat: Double,
    var lon: Double
)

data class Forecast(
        val city: City,
        val cnt: Int,
        val cod: String,
        val list: List<Prediction>,
        val message: Double
)

data class Prediction(
        val clouds: Clouds,
        val dt: Int,
        val dt_txt: String,
        val main: Main,
        val rain: Rain,
        val sys: Sys,
        val weather: List<Weather>,
        val wind: Wind
)

data class City(
        val coord: Coord,
        val country: String,
        val name: String
)