package com.airnow.di.main

import com.airnow.ui.module.feed.FeedFragment
import com.airnow.ui.module.forecast.ForecastFragment
import com.airnow.ui.module.licences.LicencesFragment
import com.airnow.ui.module.location.LocationFragment
import com.airnow.ui.module.location.add.AddDialog
import com.airnow.ui.module.menu.BottomDrawer
import com.airnow.ui.module.weather.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @ContributesAndroidInjector()
    abstract fun contributeNewsFragment(): FeedFragment

    @ContributesAndroidInjector()
    abstract fun contributeBottomDrawerFragment(): BottomDrawer

    @ContributesAndroidInjector()
    abstract fun contributeLicencesFragment(): LicencesFragment

    @ContributesAndroidInjector()
    abstract fun contributeLocationFragment(): LocationFragment

    @ContributesAndroidInjector()
    abstract fun contributeAddDialog(): AddDialog

    @ContributesAndroidInjector()
    abstract fun contributeWeatherFragment(): WeatherFragment

    @ContributesAndroidInjector()
    abstract fun contributeForecastFragment(): ForecastFragment

}