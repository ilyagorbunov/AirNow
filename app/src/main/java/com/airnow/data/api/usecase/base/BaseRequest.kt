package com.airnow.data.api.usecase.base

interface BaseRequest {
    fun validate(): Boolean
}
