package com.airnow.ui.module.feed

import com.airnow.data.model.Post

interface PostClickListener {

    fun onItemClick(post: Post)

    fun onShareClick(post: Post)

    fun onBookmarkClick(post: Post)
}