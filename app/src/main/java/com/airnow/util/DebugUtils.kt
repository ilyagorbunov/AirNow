package com.airnow.util

import android.util.Log
import com.airnow.BuildConfig
import com.crashlytics.android.Crashlytics
import java.lang.Exception

private const val TAG = "AirNow"

/**
 * Exception logger.
 *
 * @param exception
 * @param message
 */
fun logThrowable(throwable: Throwable) {
    if (BuildConfig.DEBUG) {
        throwable.printStackTrace()
    } else {
        Crashlytics.logException(throwable)
    }
}

/**
 * Debug console logger.
 *
 * @param message
 */
fun logConsole(message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(TAG, message)
    }
}
