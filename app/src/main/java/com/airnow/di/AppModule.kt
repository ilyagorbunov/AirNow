package com.airnow.di

import android.content.Context
import android.content.SharedPreferences
import com.airnow.App
import com.airnow.di.api.ApiModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        (DatabaseModule::class),
        (ApiModule::class),
        (ViewModelModule::class),
        (DomainModule::class)
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(app: App): SharedPreferences =
        app.getSharedPreferences("an_sharedpreferences", Context.MODE_PRIVATE)
}