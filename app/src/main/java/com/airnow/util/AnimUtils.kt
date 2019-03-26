package com.airnow.util

import com.airnow.App
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimUtils @Inject constructor(appContext: App) {

    companion object {
        const val MEDIUM_ANIM_DURATION = 500L
        const val SHORT_ANIM_DURATION = 150L
    }

}