package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Source
import com.airnow.databinding.ListItemSourceBinding
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelClickListener
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.viewholder.SourceViewHolder

/**
 * UI model for feed sources.
 */
class SourceUIModel(
        private val source: Source,
        private val clickListener: UIModelClickListener<Source>
) : DiffableUIModel<SourceViewHolder> {

    override fun getViewHolder(parent: ViewGroup): SourceViewHolder {
        return SourceViewHolder(
                ListItemSourceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun bindViewHolder(viewHolder: SourceViewHolder) {
        viewHolder.bind(source, clickListener)
    }

    override fun getViewType(): Int = UIModelType.SOURCE.ordinal

    override fun getData() = source
}