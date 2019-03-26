package com.airnow.data.repo

import com.airnow.data.DataStatus
import com.airnow.data.api.INetworkDataSource
import com.airnow.data.api.usecase.weather.GetCurrentWeatherByNameRequest
import com.airnow.data.api.usecase.weather.getWeatherList.GetWeatherListRequest
import com.airnow.data.db.dao.WeatherDao
import com.airnow.data.model.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepo @Inject constructor(
        private val networkDataSource: INetworkDataSource,
        private val weatherDao: WeatherDao
) {

    suspend fun getCurrentWeather(request: GetCurrentWeatherByNameRequest): DataStatus<Weather> {
        val result = networkDataSource.getCurrentWeatherByName(request.cityName)

        // Save in database
        if (result is DataStatus.Successful) {
            val weather = (result as DataStatus.Successful).data
            weather?.let { weatherDao.insertWeather(it) }
        }

        return result
    }

    suspend fun getWeatherList(request: GetWeatherListRequest): DataStatus<List<DataStatus<Weather>>> {
        var weatherList = mutableListOf<DataStatus<Weather>>()

        for (location in request.locationList) {
            val result = networkDataSource.getCurrentWeatherByName(location.cityName)
            weatherList.add(result)

            // Save in database
            if (result is DataStatus.Successful) {
                val currentWeather = (result as DataStatus.Successful).data
                currentWeather?.let { weatherDao.insertWeather(it) }
            }

        }

        val fromDisk = weatherDao.getAllWeatherList()

        return DataStatus.Successful(weatherList)
    }
}