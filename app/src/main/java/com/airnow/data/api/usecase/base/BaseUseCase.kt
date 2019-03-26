package com.airnow.data.api.usecase.base

import com.airnow.data.DataStatus


abstract class BaseUseCase<R : BaseRequest, T>() {

    var request: R? = null

    suspend fun execute(request: R? = null): DataStatus<T> {
        this.request = request

        val validated = request?.validate() ?: true
        if (validated) return run()
        return DataStatus.Failed(IllegalArgumentException())
    }

    abstract suspend fun run(): DataStatus<T>
}
