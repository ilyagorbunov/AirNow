package com.airnow.data.model.factory

import com.airnow.data.model.Weather
import com.google.gson.Gson

object WeatherFactory {

    val currentTemp = "30.0"

    val currentWeatherResponse = "{\"coord\":\n" +
            "{\"lon\":145.77,\"lat\":-16.92},\n" +
            "\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\n" +
            "\"base\":\"cmc stations\",\n" +
            "\"main\":{\"temp\":$currentTemp,\"pressure\":1019,\"humidity\":83,\"temp_min\":289.82,\"temp_max\":295.37},\n" +
            "\"wind\":{\"speed\":5.1,\"deg\":150},\n" +
            "\"clouds\":{\"all\":75},\n" +
            "\"rain\":{\"3h\":3},\n" +
            "\"dt\":1435658272,\n" +
            "\"sys\":{\"type\":1,\"id\":8166,\"message\":0.0166,\"country\":\"AU\",\"sunrise\":1435610796,\"sunset\":1435650870},\n" +
            "\"id\":2172797,\n" +
            "\"name\":\"${LocationFactory.locationTest}\",\n" +
            "\"cod\":200}"

    fun createCurrentWeatherTest(): Weather {
        var gson = Gson()
        return gson?.fromJson(currentWeatherResponse, Weather::class.java)
    }

}