package com.airnow.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.airnow.R
import com.airnow.data.model.Post
import com.airnow.ui.module.feed.PostClickListener
import com.airnow.util.glide.GlideApp
import com.airnow.util.glide.roundCorners

/**
 * ViewHolder to display article cards.
 */
abstract class PostViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

    private val cornerSize = root.resources.getDimension(R.dimen.card_corner_radius).toInt()

    /**
     * Binds the post to the ViewHolder.
     *
     * @param post
     * @param postClickListener
     */
    abstract fun bind(
        post: Post,
        postClickListener: PostClickListener
    )

    protected fun bindImage(
        imageView: ImageView,
        post: Post
    ) {
        GlideApp.with(root.context)
            .load(post.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .roundCorners(cornerSize)
            .into(imageView)
    }
}