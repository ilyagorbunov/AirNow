package com.airnow.data.model.factory

import com.airnow.data.model.Location

object LocationFactory {
    val locationTest = "Zaragoza"

    fun createCurrentLocationTest(): Location {
        return Location(0, locationTest)
    }

    fun createLocationList(): List<Location> {
        var locationList = mutableListOf<Location>()
        locationList.add(createCurrentLocationTest())
        return locationList
    }

}