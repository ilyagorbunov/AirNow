package com.airnow.data.api

import com.airnow.BuildConfig
import com.airnow.data.model.Forecast
import com.airnow.data.model.Weather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {
    companion object {
        // PATH
        const val URL_PATH_CURRENT_WEATHER = "/data/2.5/weather"
        const val URL_PATH_CURRENT_FORECAST = "/data/2.5/forecast"

        // PARAMETERS
        const val URL_PARAMETER_CITY_ID = "id"
        const val URL_PARAMETER_CITY_NAME = "q"
        const val URL_PARAMETER_LICENSE_TOKEN = "APPID"
        const val URL_PARAMETER_UNITS = "units"
    }

    @GET(OpenWeatherAPI.URL_PATH_CURRENT_WEATHER)
    fun currentWeatherByName(
            @Query(URL_PARAMETER_CITY_NAME) cityName: String = "",
            @Query(URL_PARAMETER_LICENSE_TOKEN) appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
            @Query(URL_PARAMETER_UNITS) units: String = "metric"
    ): Deferred<Weather>

    @GET(OpenWeatherAPI.URL_PATH_CURRENT_FORECAST)
    fun forecastByName(
        @Query(URL_PARAMETER_CITY_NAME) cityName: String = "",
        @Query(URL_PARAMETER_LICENSE_TOKEN) appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query(URL_PARAMETER_UNITS) units: String = "metric"
    ): Deferred<Forecast>


}

