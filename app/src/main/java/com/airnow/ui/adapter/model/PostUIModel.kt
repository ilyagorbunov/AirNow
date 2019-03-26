package com.airnow.ui.adapter.model

import android.view.LayoutInflater
import android.view.ViewGroup
import com.airnow.data.model.Post
import com.airnow.databinding.ListItemPostLargeBinding
import com.airnow.databinding.ListItemPostSmallBinding
import com.airnow.ui.adapter.DiffableUIModel
import com.airnow.ui.adapter.UIModelType
import com.airnow.ui.adapter.viewholder.PostLargeViewHolder
import com.airnow.ui.adapter.viewholder.PostSmallViewHolder
import com.airnow.ui.adapter.viewholder.PostViewHolder
import com.airnow.ui.module.feed.PostClickListener

data class PostUIModel(
    private val post: Post,
    private val onPostClickListener: PostClickListener
) : DiffableUIModel<PostViewHolder> {

    override fun getViewHolder(parent: ViewGroup) =
        when (post.layoutType) {
            UIModelType.POST_SMALL -> PostSmallViewHolder(
                ListItemPostSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> PostLargeViewHolder(
                ListItemPostLargeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun bindViewHolder(viewHolder: PostViewHolder) {
        viewHolder.bind(post, onPostClickListener)
    }

    override fun getViewType(): Int = post.layoutType.ordinal

    override fun getData() = post
}