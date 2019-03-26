package com.airnow.ui.module.location.add

import androidx.lifecycle.MutableLiveData
import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationRequest
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import com.airnow.data.model.Location
import com.airnow.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddViewModel @Inject constructor(
        private val saveWeatherLocationUseCase: SaveWeatherLocationUseCase
): BaseViewModel() {

    var saveWeatherLocationdStateLiveData = MutableLiveData<DataStatus<List<Location>>>()

    fun saveWeatherLocation(location: Location) = launch(Dispatchers.IO) {
        val request = SaveWeatherLocationRequest(location)
        val response = saveWeatherLocationUseCase.execute(request)
        processSaveWeatherLocationResponse(response)
    }

    fun processSaveWeatherLocationResponse(response: DataStatus<List<Location>>){
        if (response is DataStatus.Successful) {
            saveWeatherLocationdStateLiveData.postValue(DataStatus.Successful(response.data))
        } else if (response is DataStatus.Successful) {
            saveWeatherLocationdStateLiveData.postValue(DataStatus.Failed())
        }
    }

}