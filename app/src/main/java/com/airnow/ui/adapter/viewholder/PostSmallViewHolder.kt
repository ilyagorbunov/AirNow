package com.airnow.ui.adapter.viewholder

import com.airnow.data.model.Post
import com.airnow.databinding.ListItemPostSmallBinding
import com.airnow.ui.module.feed.PostClickListener

/**
 * [PostViewHolder] for the small article cards.
 *
 * @param binding
 */
class PostSmallViewHolder(
    private val binding: ListItemPostSmallBinding
) : PostViewHolder(binding.root) {

    override fun bind(
        post: Post,
        postClickListener: PostClickListener
    ) {
        binding.postClickListener = postClickListener
        binding.post = post
        binding.executePendingBindings()

        bindImage(binding.imgPost, post)
    }
}