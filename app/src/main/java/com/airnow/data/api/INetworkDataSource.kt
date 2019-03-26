package com.airnow.data.api

import com.airnow.data.DataStatus
import com.airnow.data.model.Forecast
import com.airnow.data.model.Weather


open abstract class INetworkDataSource(private val networkSystem: NetworkSystemAbstract) {

    abstract suspend fun getCurrentWeatherByName(cityName:String): DataStatus<Weather>

    abstract suspend fun getForecast(cityName:String): DataStatus<Forecast>
}