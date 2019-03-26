package com.airnow.di

import com.airnow.di.main.MainFragmentModule
import com.airnow.di.main.MainModule
import com.airnow.ui.module.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

}