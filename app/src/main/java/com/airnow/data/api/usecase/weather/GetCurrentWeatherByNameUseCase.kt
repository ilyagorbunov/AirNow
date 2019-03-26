package com.airnow.data.api.usecase.weather

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Weather
import com.airnow.data.repo.WeatherRepo

open class GetCurrentWeatherByNameUseCase(val repository: WeatherRepo)
    : BaseUseCase<GetCurrentWeatherByNameRequest, Weather>() {

    override suspend fun run(): DataStatus<Weather> {
        return repository.getCurrentWeather(request!!)
    }
}