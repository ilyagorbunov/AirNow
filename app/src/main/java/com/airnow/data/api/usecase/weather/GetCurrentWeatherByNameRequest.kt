package com.airnow.data.api.usecase.weather

import com.airnow.data.api.usecase.base.BaseRequest


class GetCurrentWeatherByNameRequest(var cityName: String) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}