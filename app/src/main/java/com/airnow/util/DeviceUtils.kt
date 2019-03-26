package com.airnow.util

import android.os.Build

/**
 * Returns true if the device is running Marshmallow or higher OS version.
 */
fun isMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
