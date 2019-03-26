package com.airnow.di

import com.airnow.data.api.usecase.forecast.GetForecastUseCase
import com.airnow.data.api.usecase.location.currentLocation.GetCurrentLocationUseCase
import com.airnow.data.api.usecase.location.deleteWeatherLocation.DeleteWeatherLocationUseCase
import com.airnow.data.api.usecase.location.getAllWeatherLocationList.GetAllWeatherLocationListUseCase
import com.airnow.data.api.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import com.airnow.data.api.usecase.weather.GetCurrentWeatherByNameUseCase
import com.airnow.data.api.usecase.weather.getWeatherList.GetWeatherListUseCase
import com.airnow.data.repo.ForecastRepo
import com.airnow.data.repo.LocationRepo
import com.airnow.data.repo.WeatherRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repository: WeatherRepo) = GetCurrentWeatherByNameUseCase(repository)

    @Provides
    @Singleton
    fun provideGetWeatherListUseCase(repository: WeatherRepo) = GetWeatherListUseCase(repository)


    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(repository: LocationRepo) = GetCurrentLocationUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllWeatherLocationListUseCase(repository: LocationRepo) = GetAllWeatherLocationListUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveWeatherLocationUseCase(repository: LocationRepo) = SaveWeatherLocationUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteWeatherLocationUseCase(repository: LocationRepo) = DeleteWeatherLocationUseCase(repository)

    @Provides
    @Singleton
    fun provideGetForecastUseCase(repository: ForecastRepo) = GetForecastUseCase(repository)

}