package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Location
import com.airnow.databinding.ListItemForecastBinding
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.diff.Diffable
import com.airnow.ui.adapter.viewholder.ForecastViewHolder

data class ForecastUIModel(
        private val location: Location,
        private val listener: (Boolean) -> Unit
) : DiffableUIModel<ForecastViewHolder> {

    override fun getViewHolder(parent: ViewGroup): ForecastViewHolder {
        return ForecastViewHolder(
            ListItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(viewHolder: ForecastViewHolder) {
        viewHolder.bind(location, listener)
    }

    override fun getViewType(): Int = UIModelType.WEATHER.ordinal

    override fun getData(): Diffable = location
}