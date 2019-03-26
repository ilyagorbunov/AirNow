package com.airnow.data.api.usecase.weather.getWeatherList

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Weather
import com.airnow.data.repo.WeatherRepo

open class GetWeatherListUseCase(val repository: WeatherRepo)
    : BaseUseCase<GetWeatherListRequest, List<DataStatus<Weather>>>() {

    override suspend fun run(): DataStatus<List<DataStatus<Weather>>> {
        return repository.getWeatherList(request!!)
    }

}