package com.airnow.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.airnow.data.model.Source
import com.airnow.databinding.ListItemSourceBinding
import com.airnow.ui.adapter.UIModelClickListener

class SourceViewHolder(
    private val binding: ListItemSourceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        source: Source,
        clickListener: UIModelClickListener<Source>
    ) {
        binding.source = source
        binding.clickListener = clickListener
    }
}