package com.airnow.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.airnow.data.model.Location
import com.airnow.databinding.ListItemForecastBinding

class ForecastViewHolder(
        private val binding: ListItemForecastBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
            location: Location,
            deleteListener: (Boolean) -> Unit
    ) {
//        binding.location = location
//        binding.imageDelete.setOnClickListener {
//            deleteListener(true)
//        }
    }

}