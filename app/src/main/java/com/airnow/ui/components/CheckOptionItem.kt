package com.airnow.ui.components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airnow.R
import kotlinx.android.synthetic.main.check_option_item.view.*

class CheckOptionItem: ConstraintLayout {

    private val textView: TextView
    private val imageView:ImageView

    constructor(context: Context,attributeSet: AttributeSet):this(context,attributeSet, R.attr.optionItemStyle)

    constructor(context: Context,attrs: AttributeSet,param:Int):super(context,attrs,param) {

        val localLayout = LayoutInflater.from(context).inflate(R.layout.check_option_item,this)
        textView = localLayout.option_text
        imageView = localLayout.option_check

        val styles = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckOptionItem,param,0)
        try {
            textView.text = styles.getString(R.styleable.CheckOptionItem_text)
            textView.textSize = 13f
            textView.setTextColor(context.resources.getColor(R.color.grayDark2))
        } finally {
            styles.recycle()
        }
    }

    var isChecked:Boolean
        get() = imageView.visibility == View.VISIBLE
        set(value) = if (value) {
            imageView.visibility = View.VISIBLE
            textView.setTypeface(null, Typeface.BOLD)
        } else {
            imageView.visibility = View.INVISIBLE
            textView.setTypeface(null, Typeface.NORMAL)
        }
}