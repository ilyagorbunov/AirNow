package com.airnow.ui.module.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.airnow.databinding.FragmentForecastBinding
import com.airnow.ui.adapter.UIModelAdapter
import com.airnow.ui.common.BaseFragment
import com.airnow.ui.common.WrapContentLinearLayoutManager

class ForecastFragment : BaseFragment("forecast") {

    private lateinit var forecastViewModel: ForecastViewModel
    private lateinit var binding: FragmentForecastBinding

    private val linearLayoutManager = LinearLayoutManager(context)
    private val forecastAdapter: UIModelAdapter by lazy {
        UIModelAdapter(
                this,
                linearLayoutManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ForecastViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentForecastBinding.inflate(
                inflater,
                container,
                false
        ).apply {
            lifecycleOwner = this@ForecastFragment
        }

        initForecast()

        return binding.root
    }

    private fun initForecast() {
        binding.recyclerView.apply {
            layoutManager = WrapContentLinearLayoutManager(requireContext())
            swapAdapter(
                    forecastAdapter,
                    false
            )
        }
    }
}