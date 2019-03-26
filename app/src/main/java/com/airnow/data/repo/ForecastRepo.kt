package com.airnow.data.repo

import com.airnow.data.DataStatus
import com.airnow.data.api.INetworkDataSource
import com.airnow.data.api.usecase.forecast.GetForecastRequest
import com.airnow.data.model.Forecast
import javax.inject.Singleton

@Singleton
class ForecastRepo(
    private val networkDataSource: INetworkDataSource
) {

    suspend fun getForecast(request: GetForecastRequest): DataStatus<Forecast> {
        val result = networkDataSource.getForecast(request.cityName)
        return result
    }
}