package com.airnow.ui.module.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.airnow.data.DataStatus
import com.airnow.data.model.Location
import com.airnow.databinding.FragmentLocationBinding
import com.airnow.ui.adapter.BaseUIModelAlias
import com.airnow.ui.adapter.UIModelAdapter
import com.airnow.ui.common.BaseFragment
import com.airnow.ui.common.WrapContentLinearLayoutManager
import com.airnow.ui.module.feed.FeedType
import com.airnow.ui.module.location.add.AddDialog
import com.airnow.ui.module.main.MainViewModel
import com.airnow.util.event.EventObserver

class LocationFragment : BaseFragment("location") {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentLocationBinding

    private val linearLayoutManager = LinearLayoutManager(context)
    private val locationAdapter: UIModelAdapter by lazy {
        UIModelAdapter(
                this,
                linearLayoutManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding = FragmentLocationBinding.inflate(
                inflater,
                container,
                false
        ).apply {
            lifecycleOwner = this@LocationFragment
        }

        subscribeLocationUIModels()
        subscribeOpenAddLocation()
        subscribeLocationList()
        subscribeDeleteLocation()
        subscribeUpdateLocationsEvent()

        initLocations()

        return binding.root
    }

    private fun initLocations() {
        binding.recyclerView.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            swapAdapter(
                    locationAdapter,
                    false
            )
        }
    }

    private fun subscribeLocationUIModels() {
        locationViewModel.locationUIModels.observe(viewLifecycleOwner, Observer { uiModels ->
            locationAdapter.addUIModels(uiModels as List<BaseUIModelAlias>)
        })
    }

    private fun subscribeOpenAddLocation() {
        locationViewModel.openAddLocationDialog.observe(viewLifecycleOwner, EventObserver {
            fragmentManager?.let { AddDialog.display(it) }
        })
    }


    private fun subscribeLocationList() {
        locationViewModel.locationListLiveData.observe(this, Observer<DataStatus<List<Location>>> { state ->
            state?.let {
                when (state) {
                    is DataStatus.Successful -> {
                        //hideLoading()
                        state.data?.let { it1 -> locationViewModel.updateLocationUIModels(it1) }
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

    private fun subscribeDeleteLocation() {
        locationViewModel.deleteLocation.observe(viewLifecycleOwner, EventObserver { location ->
            locationViewModel.deleteWeatherLocation(location)
        })
    }

    private fun subscribeUpdateLocationsEvent() {
        mainViewModel.onUpdateLocations.observe(viewLifecycleOwner, Observer {
            locationViewModel.getWeatherLocationList()
        })
    }


}