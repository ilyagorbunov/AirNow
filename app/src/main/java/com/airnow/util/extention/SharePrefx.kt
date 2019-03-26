package com.airnow.util.extention

import android.content.SharedPreferences
import androidx.core.content.edit

internal var SharedPreferences.selectedItem: Int
    set(value) {
        edit { putInt("selectedItem", value) }
    }
    get() {
        return getInt("selectedItem", -1)
    }