package com.airnow.data.api.usecase.location.saveWeatherLocation

import com.airnow.data.api.usecase.base.BaseRequest
import com.airnow.data.model.Location

class SaveWeatherLocationRequest(var location: Location) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}