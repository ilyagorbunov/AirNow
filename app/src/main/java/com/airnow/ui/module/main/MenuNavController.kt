package com.airnow.ui.module.main

import androidx.fragment.app.Fragment
import com.airnow.R
import com.airnow.data.model.Weather
import com.airnow.di.MainScope
import com.airnow.ui.module.feed.FeedFragment
import com.airnow.ui.module.licences.LicencesFragment
import com.airnow.ui.module.location.LocationFragment
import com.airnow.ui.module.weather.WeatherFragment
import javax.inject.Inject

@MainScope
class MenuNavController @Inject constructor(val activity: MainActivity) {

    private val fragmentManager = activity.supportFragmentManager
    private val containerId = R.id.fragmentContainer

    var activeDestination: Destination? = null
        private set

    private val feedFragment: FeedFragment by lazy { FeedFragment() }
    private val licencesFragment: LicencesFragment by lazy { LicencesFragment() }
    private val addLocationFragment: LocationFragment by lazy { LocationFragment() }
    private val weatherFragment: WeatherFragment by lazy { WeatherFragment() }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.animator.fade_in,
                android.R.animator.fade_out
            )
            .replace(containerId, fragment)
            .commit()
    }

    fun open(destination: Destination) {
        if (activeDestination != destination) {
            activeDestination = destination

            when (destination) {
                Destination.FEED -> changeFragment(feedFragment)
                Destination.OPEN_SOURCE_LICENSES -> changeFragment(licencesFragment)
                Destination.ADD_LOCATION -> changeFragment(addLocationFragment)
                Destination.WEATHER -> changeFragment(weatherFragment)
            }
        }
    }
}