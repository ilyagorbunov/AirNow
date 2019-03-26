package com.airnow.data.api.usecase.location.deleteWeatherLocation

import com.airnow.data.api.usecase.base.BaseRequest
import com.airnow.data.model.Location

class DeleteWeatherLocationRequest(var location: Location) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}