package com.airnow.ui.adapter.viewholder

import com.airnow.data.model.Post
import com.airnow.databinding.ListItemPostLargeBinding
import com.airnow.ui.module.feed.PostClickListener

/**
 * [PostViewHolder] for the large article cards.
 */
class PostLargeViewHolder(
    private val binding: ListItemPostLargeBinding
) : PostViewHolder(binding.root) {

    override fun bind(post: Post, postClickListener: PostClickListener) {
        binding.postClickListener = postClickListener
        binding.post = post
        binding.executePendingBindings()

        bindImage(binding.imgPost, post)
    }
}