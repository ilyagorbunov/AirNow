package com.airnow.data.api.usecase.forecast

import com.airnow.data.api.usecase.base.BaseRequest

class GetForecastRequest(var cityName: String) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}