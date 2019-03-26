package com.airnow.data.api.usecase.location.getAllWeatherLocationList

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.base.BaseUseCase
import com.airnow.data.model.Location
import com.airnow.data.repo.LocationRepo

open class GetAllWeatherLocationListUseCase(val repository: LocationRepo) : BaseUseCase<Nothing, List<Location>>() {

    override suspend fun run(): DataStatus<List<Location>> {
        return repository.getAllWeatherLocationList()
    }

}