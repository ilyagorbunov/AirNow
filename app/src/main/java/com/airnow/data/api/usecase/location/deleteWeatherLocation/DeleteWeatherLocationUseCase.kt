package com.airnow.data.api.usecase.location.deleteWeatherLocation

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Location
import com.airnow.data.repo.LocationRepo

open class DeleteWeatherLocationUseCase(val repository: LocationRepo)
    : BaseUseCase<DeleteWeatherLocationRequest, List<Location>>() {

    override suspend fun run(): DataStatus<List<Location>> {
        return repository.deleteWeatherLocation(request!!)
    }
}