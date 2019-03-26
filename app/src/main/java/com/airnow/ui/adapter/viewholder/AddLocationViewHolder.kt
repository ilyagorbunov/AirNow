package com.airnow.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.airnow.databinding.ListItemAddLocationBinding

class AddLocationViewHolder(
        private val binding: ListItemAddLocationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
            addListener: (Boolean) -> Unit
    ) {
        binding.root.setOnClickListener {
            addListener(true)
        }
    }

}