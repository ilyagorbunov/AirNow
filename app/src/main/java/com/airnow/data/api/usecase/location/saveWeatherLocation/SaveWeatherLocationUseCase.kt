package com.airnow.data.api.usecase.location.saveWeatherLocation

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Location
import com.airnow.data.repo.LocationRepo

open class SaveWeatherLocationUseCase(val repository: LocationRepo)
    : BaseUseCase<SaveWeatherLocationRequest, List<Location>>() {

    override suspend fun run(): DataStatus<List<Location>> {
        return repository.saveWeatherLocationList(request!!)
    }
}