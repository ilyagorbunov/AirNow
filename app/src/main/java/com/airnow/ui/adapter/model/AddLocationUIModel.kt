package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Location
import com.airnow.databinding.ListItemAddLocationBinding
import com.airnow.ui.adapter.BaseUIModel
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.diff.Diffable
import com.airnow.ui.adapter.viewholder.AddLocationViewHolder

data class AddLocationUIModel(
        private val listener: (Boolean) -> Unit
) : DiffableUIModel<AddLocationViewHolder> {

    override fun getViewHolder(parent: ViewGroup): AddLocationViewHolder {
        return AddLocationViewHolder(
            ListItemAddLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(viewHolder: AddLocationViewHolder) {
        viewHolder.bind(listener)
    }

    override fun getViewType(): Int = UIModelType.ADD_LOCATION.ordinal

    override fun getData(): Diffable = Location()
}