package com.airnow.data.api.usecase.weather.getWeatherList

import com.airnow.data.api.usecase.base.BaseRequest
import com.airnow.data.model.Location

class GetWeatherListRequest(var locationList: List<Location>) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}