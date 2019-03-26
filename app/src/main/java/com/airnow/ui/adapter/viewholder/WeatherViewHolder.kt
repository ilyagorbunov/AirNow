package com.airnow.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.airnow.data.model.Weather
import com.airnow.databinding.ListItemWeatherBinding

class WeatherViewHolder(
        private val binding: ListItemWeatherBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
            weather: Weather,
            deleteListener: (Boolean) -> Unit
    ) {
        binding.weather = weather
    }
}