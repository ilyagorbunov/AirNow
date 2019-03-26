package com.airnow.di.main

import com.airnow.ui.module.main.MainActivity
import com.airnow.util.CustomTab
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun providesCustomTab(activity: MainActivity) = CustomTab(activity)

}