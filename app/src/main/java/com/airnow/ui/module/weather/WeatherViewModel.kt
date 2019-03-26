package com.airnow.ui.module.weather

import androidx.lifecycle.MutableLiveData
import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.location.currentLocation.GetCurrentLocationUseCase
import com.airnow.data.api.usecase.weather.GetCurrentWeatherByNameRequest
import com.airnow.data.api.usecase.weather.GetCurrentWeatherByNameUseCase
import com.airnow.data.api.usecase.weather.getWeatherList.GetWeatherListRequest
import com.airnow.data.api.usecase.weather.getWeatherList.GetWeatherListUseCase
import com.airnow.data.model.Location
import com.airnow.data.model.Weather
import com.airnow.data.model.factory.WeatherFactory
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.model.WeatherUIModel
import com.airnow.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
        private val getCurrentWeatherByNameUseCase: GetCurrentWeatherByNameUseCase,
        private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
        private val getWeatherListUseCase: GetWeatherListUseCase
        ) : BaseViewModel() {

    var weatherListLiveData = MutableLiveData<List<DataStatus<Weather>>>()
    val weatherUIModels = MutableLiveData<List<BaseUIModelAlias>>()

    var currentWeatherStateLiveData = MutableLiveData<DataStatus<Weather>>()

    fun updateWeatherUIModels(weatherList: List<Weather>) = launch {
        val listUiModels: MutableList<BaseUIModelAlias> = mutableListOf()

        val uiModels = weatherList.map { weather ->
            WeatherUIModel(weather) { selected ->
//                deleteLocation.postValue(Event(selected))
            }
        }

        listUiModels.addAll(uiModels as List<BaseUIModelAlias>)
        weatherUIModels.postValue(listUiModels)
    }

    fun getWeatherList(locationList: List<Location>) = launch(Dispatchers.IO) {
        val request = GetWeatherListRequest(locationList)
        val response = getWeatherListUseCase.execute(request)
        processWeatherListResponse(response)
    }

    private fun processWeatherListResponse(response: DataStatus<List<DataStatus<Weather>>>) {
        if (response is DataStatus.Successful) {
            var weatherList = mutableListOf<DataStatus<Weather>>()

            for (responseWeather in response.data!!) {
                if (responseWeather is DataStatus.Successful) {
                    weatherList.add(responseWeather)
                }
            }
            weatherListLiveData.postValue(weatherList)
        }

        else if (response is DataStatus.Failed) {
        }
    }

    fun getCityCurrentWeather(cityName: String) = launch(Dispatchers.IO) {
        currentWeatherStateLiveData
                .postValue(DataStatus.Successful(WeatherFactory.createCurrentWeatherTest()))

        val request = GetCurrentWeatherByNameRequest(cityName)
        val response = getCurrentWeatherByNameUseCase.execute(request)
        proccessCurrentWeather(response)
    }

    private fun proccessCurrentWeather(response: DataStatus<Weather>){
        if (response is DataStatus.Successful) {
            currentWeatherStateLiveData.postValue(response)
        } else if (response is DataStatus.Failed) {
            currentWeatherStateLiveData.postValue(response)
        }
    }
}