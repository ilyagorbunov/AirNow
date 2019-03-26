package com.airnow.util.extention

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.airnow.R
import com.airnow.util.AnimUtils

/**
 * Loads given image url into the ImageView via Glide.
 *
 * @param urlOrResource
 */
fun ImageView.loadImage(urlOrResource: Any) {
    Glide.with(context)
        .load(urlOrResource)
        .apply(
            RequestOptions()
                .error(context.getDrawable(R.drawable.ic_broken_image_black_24dp))
        )
        .into(this)
}

/**
 * Hides the soft keyboard.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.fadeIn(duration: Long = AnimUtils.SHORT_ANIM_DURATION) {
    if (isVisible && alpha != 1f) {
        animate().apply {
            this.duration = duration
            alpha(1f)
            start()
        }
    }
}

fun View.fadeOut(
        toAlpha: Float = 0f,
        duration: Long = AnimUtils.SHORT_ANIM_DURATION
) {
    if (isVisible && alpha != toAlpha) {
        animate().apply {
            this.duration = duration
            alpha(toAlpha)
            start()
        }
    }
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}