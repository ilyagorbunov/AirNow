package com.airnow.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.airnow.ui.common.ANViewModelFactory
import com.airnow.ui.module.feed.FeedViewModel
import com.airnow.ui.module.forecast.ForecastViewModel
import com.airnow.ui.module.licences.LicencesViewModel
import com.airnow.ui.module.location.LocationViewModel
import com.airnow.ui.module.location.add.AddViewModel
import com.airnow.ui.module.main.MainViewModel
import com.airnow.ui.module.menu.BottomDrawerModel
import com.airnow.ui.module.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BottomDrawerModel::class)
    abstract fun bindBottomDrawerViewModel(viewModel: BottomDrawerModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LicencesViewModel::class)
    abstract fun bindLicencesViewModel(viewModel: LicencesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindLocationViewModel(viewModel: LocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddViewModel::class)
    abstract fun bindAddViewModel(viewModel: AddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(viewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    abstract fun bindForecastViewModel(viewModel: ForecastViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ANViewModelFactory): ViewModelProvider.Factory

}
