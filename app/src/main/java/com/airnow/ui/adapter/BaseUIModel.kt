package com.airnow.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airnow.ui.adapter.diff.Diffable

/**
 * Base UI model to be used with [UIModelAdapter].
 */
interface BaseUIModel<T : RecyclerView.ViewHolder> {

    fun getViewHolder(parent: ViewGroup): T

    fun bindViewHolder(viewHolder: T)

    fun getViewType(): Int

}