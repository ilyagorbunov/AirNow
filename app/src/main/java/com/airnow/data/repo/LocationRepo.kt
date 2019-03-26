package com.airnow.data.repo

import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.location.deleteWeatherLocation.DeleteWeatherLocationRequest
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationRequest
import com.airnow.data.db.dao.LocationDao
import com.airnow.data.model.Location
import com.airnow.data.model.factory.LocationFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationRepo @Inject constructor(
    private val locationDao: LocationDao
) {

    fun getCurrentLocation(): DataStatus<Location> {
        return DataStatus.Successful(LocationFactory.createCurrentLocationTest())
    }

    fun saveWeatherLocationList(request: SaveWeatherLocationRequest): DataStatus<List<Location>> {
        locationDao.insertLocation(request.location)

        val locationList = locationDao.getAllLocationList()
        return DataStatus.Successful(locationList)
    }

    fun getAllWeatherLocationList(): DataStatus<List<Location>> {
        val locationList = locationDao.getAllLocationList()
        return DataStatus.Successful(locationList)
    }

    fun deleteWeatherLocation(request: DeleteWeatherLocationRequest): DataStatus<List<Location>> {
        locationDao.deleteLocation(request.location)

        val locationList = locationDao.getAllLocationList()
        return DataStatus.Successful(locationList)
    }
}
