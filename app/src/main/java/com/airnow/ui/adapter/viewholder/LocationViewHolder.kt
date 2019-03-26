package com.airnow.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.airnow.data.model.Location
import com.airnow.databinding.ListItemLocationBinding

class LocationViewHolder(
        private val binding: ListItemLocationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
            location: Location,
            deleteListener: (Location) -> Unit
    ) {
        binding.location = location
        binding.imageDelete.setOnClickListener {
            deleteListener(location)
        }
    }

}