package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Licence
import com.airnow.databinding.ListItemLicenceBinding
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.diff.Diffable
import com.airnow.ui.adapter.viewholder.LicenceViewHolder

data class LicenceUIModel(
        private val licence: Licence,
        private val listener: (String) -> Unit
) : DiffableUIModel<LicenceViewHolder> {

    override fun getViewHolder(parent: ViewGroup): LicenceViewHolder {
        return LicenceViewHolder(
            ListItemLicenceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindViewHolder(viewHolder: LicenceViewHolder) {
        viewHolder.bind(licence, listener)
    }

    override fun getViewType(): Int = UIModelType.LICENCE.ordinal

    override fun getData(): Diffable = licence
}