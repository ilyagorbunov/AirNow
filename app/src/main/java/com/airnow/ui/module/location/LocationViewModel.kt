package com.airnow.ui.module.location

import androidx.lifecycle.MutableLiveData
import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.location.currentLocation.GetCurrentLocationUseCase
import com.airnow.data.api.usecase.location.deleteWeatherLocation.DeleteWeatherLocationRequest
import com.airnow.data.api.usecase.location.deleteWeatherLocation.DeleteWeatherLocationUseCase
import com.airnow.data.api.usecase.location.getAllWeatherLocationList.GetAllWeatherLocationListUseCase
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationRequest
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import com.airnow.data.model.Location
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.model.AddLocationUIModel
import com.airnow.ui.adapter.model.LocationUIModel
import com.airnow.ui.common.BaseViewModel
import com.airnow.util.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
        private val getAllWeatherLocationListUseCase: GetAllWeatherLocationListUseCase,
        private val saveWeatherLocationUseCase: SaveWeatherLocationUseCase,
        private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
        private val deleteWeatherLocationUseCase: DeleteWeatherLocationUseCase
) : BaseViewModel() {

    val locationUIModels = MutableLiveData<List<BaseUIModelAlias>>()
    val openAddLocationDialog = MutableLiveData<Event<Boolean>>()
    val deleteLocation = MutableLiveData<Event<Location>>()
    var currentLocationStateLiveData = MutableLiveData<DataStatus<Location>>()

    var locationListLiveData = MutableLiveData<DataStatus<List<Location>>>()

    var saveWeatherLocationdStateLiveData = MutableLiveData<DataStatus<List<Location>>>()

    private val addLocationUiModel = AddLocationUIModel { isOpen ->
        openAddLocationDialog.postValue(Event(isOpen))
    }

    init {
        fillLocationUIModels()
    }

    fun fillLocationUIModels() {
        val listUiModels: MutableList<BaseUIModelAlias> = mutableListOf()
        listUiModels.add(addLocationUiModel as BaseUIModelAlias)
        locationUIModels.postValue(listUiModels)

        getWeatherLocationList()
    }

    fun updateLocationUIModels(locations: List<Location>) = launch {
        val listUiModels: MutableList<BaseUIModelAlias> = mutableListOf()

        val uiModels = locations.map { location ->
            LocationUIModel(location) { selected ->
                deleteLocation.postValue(Event(selected))
            }
        }

        listUiModels.addAll(uiModels as List<BaseUIModelAlias>)
        listUiModels.add(addLocationUiModel as BaseUIModelAlias)

        locationUIModels.postValue(listUiModels)
    }


    fun getWeatherLocationList() = launch(Dispatchers.IO) {
        val response = getAllWeatherLocationListUseCase.execute()
        processGetWeatherLocationListResponse(response)
    }

    fun processGetWeatherLocationListResponse(response: DataStatus<List<Location>>){
        if (response is DataStatus.Successful) {
            locationListLiveData.postValue(DataStatus.Successful(response.data))
        } else if (response is DataStatus.Successful) {
            locationListLiveData.postValue(DataStatus.Failed())
        }
    }

    fun deleteWeatherLocation(location: Location) = launch(Dispatchers.IO) {
        val request = DeleteWeatherLocationRequest(location)
        val response = deleteWeatherLocationUseCase.execute(request)
        processGetWeatherLocationListResponse(response)
    }

    fun getCurrentLocation() = launch(Dispatchers.IO) {
        val response = getCurrentLocationUseCase.execute()
        processCurrentLocationResponse(response)
    }

    fun processCurrentLocationResponse(response: DataStatus<Location>){
        if (response is DataStatus.Successful) {
            currentLocationStateLiveData.postValue(response)
        } else if (response is DataStatus.Failed) {
            currentLocationStateLiveData.postValue(response)
        }
    }

    fun saveWeatherLocation(weatherLocation: Location) = launch(Dispatchers.IO) {
        val request = SaveWeatherLocationRequest(weatherLocation)
        val response = saveWeatherLocationUseCase.execute(request)
        processSaveWeatherLocationResponse(response)
    }

    fun processSaveWeatherLocationResponse(response: DataStatus<List<Location>>){
        if (response is DataStatus.Successful) {
            saveWeatherLocationdStateLiveData.postValue(response)
        } else if (response is DataStatus.Failed) {
            saveWeatherLocationdStateLiveData.postValue(response)
        }
    }


}
