package com.airnow.data.api

abstract class NetworkSystemAbstract() {
    abstract fun isNetworkAvailable(): Boolean
}