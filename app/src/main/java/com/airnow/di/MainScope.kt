package com.airnow.di

import com.airnow.ui.module.main.MainActivity
import javax.inject.Scope

/**
 * Scope of [MainActivity].
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScope