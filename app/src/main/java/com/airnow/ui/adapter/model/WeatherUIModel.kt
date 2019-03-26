package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Weather
import com.airnow.databinding.ListItemWeatherBinding
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.diff.Diffable
import com.airnow.ui.adapter.viewholder.WeatherViewHolder

data class WeatherUIModel(
        private val weather: Weather,
        private val listener: (Boolean) -> Unit
) : DiffableUIModel<WeatherViewHolder> {

    override fun getViewHolder(parent: ViewGroup): WeatherViewHolder {
        return WeatherViewHolder(
            ListItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(viewHolder: WeatherViewHolder) {
        viewHolder.bind(weather, listener)
    }

    override fun getViewType(): Int = UIModelType.WEATHER.ordinal

    override fun getData(): Diffable = weather
}