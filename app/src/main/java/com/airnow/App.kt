package com.airnow

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import com.airnow.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        initDagger()
    }

    private fun initDagger() {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}

/**§§
 * Intent for sending an email for contact purposes.
 */
val contactIntent: Intent by lazy {
    Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:moshification@gmail.com")
        putExtra(Intent.EXTRA_EMAIL, "moshification@gmail.com")
        putExtra(Intent.EXTRA_SUBJECT, "About AirNow")
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
}