package com.airnow.ui.module.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airnow.data.DataStatus
import com.airnow.data.model.Location
import com.airnow.data.model.Weather
import com.airnow.databinding.FragmentWeatherBinding
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.UIModelAdapter
import com.airnow.ui.common.BaseFragment
import com.airnow.ui.common.WrapContentLinearLayoutManager
import com.airnow.ui.module.location.LocationViewModel
import com.airnow.ui.module.main.MainViewModel

class WeatherFragment : BaseFragment("weather") {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var locationViewModel: LocationViewModel

    private lateinit var binding: FragmentWeatherBinding

    private val linearLayoutManager = LinearLayoutManager(context)
    private val weatherAdapter: UIModelAdapter by lazy {
        UIModelAdapter(
                this,
                linearLayoutManager as RecyclerView.LayoutManager?
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(WeatherViewModel::class.java)

        locationViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(LocationViewModel::class.java)

        mainViewModel = ViewModelProviders
                .of(activity!!)
                .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentWeatherBinding.inflate(
                inflater,
                container,
                false
        ).apply {
            lifecycleOwner = this@WeatherFragment
        }

        subscribeLocationList()
        subscribeWeatherList()
        subscribeLocationUIModels()

        initWeather()

        getLocationList()

        return binding.root
    }

    private fun initWeather() {
        binding.recyclerView.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            swapAdapter(
                    weatherAdapter,
                    false
            )
        }
    }

    private fun getLocationList() {
        locationViewModel.getWeatherLocationList()
    }

    private fun subscribeLocationList() {
        locationViewModel.locationListLiveData.observe(this, Observer<DataStatus<List<Location>>> { state ->
            state?.let {
                when (state) {
                    is DataStatus.Successful -> {
                        //hideLoading()
                        state.data?.let { locationList -> getWeatherForLocationList(locationList) }
                    }
                    is DataStatus.Loading -> {
                        //showLoading()
                    }
                    is DataStatus.Failed -> {
                        //hideLoading()
                        //showError((it as ErrorCurrentWeatherState))
                    }
                    else -> {
                        //hideLoading()
                    }
                }
            }
        })
    }

    private fun subscribeWeatherList() {
        weatherViewModel.weatherListLiveData.observe(this, Observer<List<DataStatus<Weather>>> { weatherStateList ->
            weatherStateList?.let {
                val weatherList = mutableListOf<Weather>()
                for (weatherState in weatherStateList) {
                    when (weatherState) {
                        is DataStatus.Successful -> {
                            weatherState.data?.let { weather -> weatherList.add(weather) }
                        }
                    }
                }
                weatherViewModel.updateWeatherUIModels(weatherList)
            }
        })
    }

    private fun subscribeLocationUIModels() {
        weatherViewModel.weatherUIModels.observe(viewLifecycleOwner, Observer { uiModels ->
            weatherAdapter.addUIModels(uiModels as List<BaseUIModelAlias>)
        })
    }

    private fun getWeatherForLocationList(locationList: List<Location>?){
        if (locationList != null && locationList.isNotEmpty()) {
            weatherViewModel.getWeatherList(locationList)
        } else {
            showEmptyLocationList()
        }
    }

    private fun showEmptyLocationList() {
//        fragment_home_layout_error.visibility = View.VISIBLE
//        fragment_home_tv_status.text = getString(R.string.empty_location)
    }

}
