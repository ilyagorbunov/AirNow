package com.airnow.data.api.usecase.forecast

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Forecast
import com.airnow.data.repo.ForecastRepo

open class GetForecastUseCase(val repository: ForecastRepo) : BaseUseCase<GetForecastRequest, Forecast>() {

    override suspend fun run(): DataStatus<Forecast> {
        return repository.getForecast(request!!)
    }
}