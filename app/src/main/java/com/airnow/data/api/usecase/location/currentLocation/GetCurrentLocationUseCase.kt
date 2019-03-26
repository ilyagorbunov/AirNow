package com.airnow.data.api.usecase.location.currentLocation

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Location
import com.airnow.data.repo.LocationRepo

open class GetCurrentLocationUseCase(val repository: LocationRepo) : BaseUseCase<Nothing, Location>() {

    override suspend fun run(): DataStatus<Location> {
        return repository.getCurrentLocation()
    }
}