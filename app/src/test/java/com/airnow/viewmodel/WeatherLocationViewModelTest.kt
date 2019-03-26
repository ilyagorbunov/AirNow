package com.airnow.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.airnow.data.DataStatus
import com.airnow.data.api.usecase.location.currentLocation.GetCurrentLocationUseCase
import com.airnow.data.api.usecase.location.deleteWeatherLocation.DeleteWeatherLocationUseCase
import com.airnow.data.api.usecase.location.getAllWeatherLocationList.GetAllWeatherLocationListUseCase
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import com.airnow.data.model.Location
import com.airnow.data.model.factory.LocationFactory
import com.airnow.ui.module.location.LocationViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations



class WeatherLocationViewModelTest {
    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit  var viewModel : LocationViewModel

    @Mock
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Mock
    lateinit var saveWeatherLocationUseCase: SaveWeatherLocationUseCase

    @Mock
    lateinit var getAllWeatherLocationListUseCase: GetAllWeatherLocationListUseCase

    @Mock
    lateinit var deleteWeatherLocationUseCase: DeleteWeatherLocationUseCase

    @Mock
    lateinit var observer: Observer<DataStatus<Location>>

    @Mock
    lateinit var observerSaveWeatherLocation: Observer<DataStatus<List<Location>>>

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner
    lateinit var lifeCycle: LifecycleRegistry

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        prepareViewModel()

        lifeCycle = LifecycleRegistry(lifeCycleOwner)
        `when` (lifeCycleOwner.lifecycle).thenReturn(lifeCycle)
        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

    }

    /**
     * Verify when Initilize viewModel, the first action is getCurrentLocation
     */
    @Test
    fun `when viewModel is initilize then get currentLocation`() {
        runBlocking {
            val response = DataStatus.Successful(LocationFactory.createCurrentLocationTest())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.getCurrentLocation()
            Mockito.verify(getCurrentLocationUseCase, Mockito.times(1)).execute()
        }
    }

    /**
     * Verify when success getting current location the state is DefaultState
     */
    @Test
    fun `when received current location then CurrrentLocationState is DefaultState`() {
        runBlocking {
            val response = DataStatus.Successful(LocationFactory.createCurrentLocationTest())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.currentLocationStateLiveData.observe(lifeCycleOwner, observer)

            viewModel.getCurrentLocation()

            Mockito.verify(observer).onChanged(response)
        }
    }

    /**
     * Verify when error getting current location the state is ErrorState
     */
    @Test
    fun `when received error getting current location then CurrrentLocationState is ErrorState`() {
        runBlocking {
            val response = DataStatus.Failed<Location>(IllegalArgumentException())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.currentLocationStateLiveData.observe(lifeCycleOwner, observer)

            viewModel.getCurrentLocation()

            Mockito.verify(observer).onChanged(response)
        }
    }

    @Test
    fun `when call saveWeatherLocation(), SaveWeatherLocationUsecase is called once`() {
        runBlocking {
            val response = DataStatus.Successful(LocationFactory.createLocationList())
            whenever(saveWeatherLocationUseCase.execute()).thenReturn(response)

            viewModel.saveWeatherLocation(LocationFactory.createCurrentLocationTest())
            Mockito.verify(saveWeatherLocationUseCase, Mockito.times(1)).execute()
        }
    }

    @Test
    fun `when received success after call saveWeatherLocation(), we receive the same weather location`() {
        runBlocking {
            val response = DataStatus.Successful(LocationFactory.createLocationList())
            whenever(saveWeatherLocationUseCase.execute()).thenReturn(response)

            viewModel.saveWeatherLocationdStateLiveData.observe(lifeCycleOwner, observerSaveWeatherLocation)

            viewModel.saveWeatherLocation(LocationFactory.createCurrentLocationTest())

            Mockito.verify(observerSaveWeatherLocation).onChanged(response)
        }
    }

    @Test
    fun `when received error after call saveWeatherLocation(), we set ErrorSaveWeatherLocationSatate`() {
        runBlocking {
            val response = DataStatus.Failed<List<Location>>(java.lang.IllegalArgumentException())
            whenever(saveWeatherLocationUseCase.execute()).thenReturn(response)

            viewModel.saveWeatherLocationdStateLiveData.observe(lifeCycleOwner, observerSaveWeatherLocation)

            viewModel.saveWeatherLocation(LocationFactory.createCurrentLocationTest())

            Mockito.verify(observerSaveWeatherLocation).onChanged(response)
        }
    }



    private fun prepareViewModel(){
        viewModel = LocationViewModel(getAllWeatherLocationListUseCase, saveWeatherLocationUseCase, getCurrentLocationUseCase, deleteWeatherLocationUseCase)
    }


}