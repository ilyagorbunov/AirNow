package com.airnow.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airnow.ui.adapter.diff.Diffable

/**
 * Base UI model to be used with [UIModelAdapter].
 */
interface DiffableUIModel<T : RecyclerView.ViewHolder> : BaseUIModel<T>{

    fun getData(): Diffable

}