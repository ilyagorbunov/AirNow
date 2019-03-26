package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Location
import com.airnow.databinding.ListItemLocationBinding
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.diff.Diffable
import com.airnow.ui.adapter.viewholder.LocationViewHolder

data class LocationUIModel(
        private val location: Location,
        private val listener: (Location) -> Unit
) : DiffableUIModel<LocationViewHolder> {

    override fun getViewHolder(parent: ViewGroup): LocationViewHolder {
        return LocationViewHolder(
            ListItemLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(viewHolder: LocationViewHolder) {
        viewHolder.bind(location, listener)
    }

    override fun getViewType(): Int = UIModelType.LOCATION.ordinal

    override fun getData(): Diffable = location
}