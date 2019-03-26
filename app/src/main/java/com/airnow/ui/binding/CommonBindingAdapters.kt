package com.airnow.ui.binding

import android.text.format.DateUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airnow.R
import com.airnow.ui.components.CheckOptionItem
import com.airnow.util.extention.fadeIn
import com.airnow.util.extention.fadeOut
import com.airnow.util.glide.GlideApp
import com.google.android.material.textfield.TextInputLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.util.*

@BindingAdapter("app:avdImageResource")
fun avdImageResource(
        imageView: ImageView,
        avdImageResource: Int
) {
    imageView.setImageResource(avdImageResource)
}

@BindingAdapter("app:isVisible")
fun isVisible(
        view: View,
        isVisible: Boolean
) {
    if (view.isVisible != isVisible)
        view.isVisible = isVisible
}

@BindingAdapter("app:isSelected")
fun isSelected(
        view: View,
        isSelected: Boolean
) {
    if (view.isSelected != isSelected) {
        view.isSelected = isSelected
    }
}

@BindingAdapter("app:isChecked")
fun isChecked(
        view: CheckOptionItem,
        isChecked: Boolean
) {
    if (view.isChecked != isChecked) {
        view.isChecked = isChecked
    }
}

@BindingAdapter("app:setPanelState")
fun setPanelState(
        view: SlidingUpPanelLayout,
        isChecked: Boolean
) {
    view.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
}

@BindingAdapter("app:isEnabled")
fun isEnabled(
        view: View,
        isEnabled: Boolean
) {
    if (view.isEnabled != isEnabled) {
        view.isEnabled = isEnabled

        if (isEnabled) {
            view.fadeIn()
        } else {
            view.fadeOut(0.5f)
        }
    }
}

@BindingAdapter("app:errorText")
fun errorText(
        view: TextInputLayout,
        stringId: Int
) {
    val string = view.context.getString(stringId)
    view.error = string
    view.isErrorEnabled = string.isNotBlank()
}

@BindingAdapter("app:loadImage")
fun loadImage(
        imageView: ImageView,
        resId: Int
) {
    GlideApp.with(imageView)
            .load(resId)
            .into(imageView)
}

@BindingAdapter("app:displayUrl")
fun displayUrl(
        webView: WebView,
        url: String
) {
    webView.loadUrl(url)
}

@BindingAdapter("app:relativeTimestamp")
fun setRelativeTimestamp(
        view: TextView,
        date: Date?
) {
    if (date != null) {
        val relativeDate = DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance(TimeZone.getDefault()).timeInMillis,
                DateUtils.SECOND_IN_MILLIS
        )

        view.text = relativeDate.toString()
    }
}

@BindingAdapter(
        value = ["app:publisher",
            "app:timestamp"],
        requireAll = true
)
fun setRelativeDate(
        view: TextView,
        publisher: String?,
        timestamp: Long?
) {
    val date = if (timestamp == null) {
        ""
    } else {
        DateUtils.getRelativeTimeSpanString(
                timestamp,
                Calendar.getInstance(TimeZone.getDefault()).timeInMillis,
                android.text.format.DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL
        )
    }

    view.text = view.context.getString(
            R.string.publisher_time,
            publisher ?: "",
            date.toString()
    )
}


@BindingAdapter(value = ["app:relativeTemp",
    "app:isMax"],
        requireAll = true)
fun setRelativeTemp(
        view: TextView,
        relativeTemp: Double?,
        isMax: Boolean
) {
    if (relativeTemp != null) {
        var tempRounded = Math.round(relativeTemp)
        view.text = if (isMax) view.context.getString(R.string.tempMax, tempRounded)
        else view.context.getString(R.string.tempMin, tempRounded)
    }
}


@BindingAdapter("app:wind")
fun setWind(
        view: TextView,
        wind: Double?
) {
    if (wind != null) {
        view.text = view.context.getString(R.string.wind, wind.toInt())
    }
}

@BindingAdapter("app:temp")
fun setBigTemp(
        view: TextView,
        temp: Double?
) {
    if (temp != null) {
        var tempRounded = Math.round(temp)
        view.text = view.context.getString(R.string.temp, tempRounded)
    }
}


@BindingAdapter(value = ["app:relativeSun",
    "app:isSunset"],
        requireAll = true)
fun setRelativeSun(
        view: TextView,
        relativeSun: Int?,
        isSunset: Boolean
) {
    if (relativeSun != null) {
        val timeSunrise = DateUtils.formatDateTime(view.context, relativeSun * 1000L, DateUtils.FORMAT_SHOW_TIME)

        view.text = if (isSunset) view.context.getString(R.string.sunset, timeSunrise)
        else view.context.getString(R.string.sunrise, timeSunrise)
    }
}