package com.airnow.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.airnow.data.model.Licence
import com.airnow.databinding.ListItemLicenceBinding

class LicenceViewHolder(
        private val binding: ListItemLicenceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
            licence: Licence,
            urlListener: (String) -> Unit
    ) {
        binding.licence = licence
        binding.container.setOnClickListener {
            urlListener(licence.licenceUrl)
        }
    }

}