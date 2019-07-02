package com.app.f49

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.app.f49.utils.Constants
import com.app.f49.utils.PreferenceUtils
import com.crashlytics.android.Crashlytics
import com.facebook.drawee.backends.pipeline.Fresco
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import java.util.*

class F49Application : MultiDexApplication() {
    init {
        instance = this
    }

    companion object {
        var instance: F49Application? = null

        fun applicationContext(): F49Application {
            return instance as F49Application
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(BuildConfig.DEBUG) // Enables Crashlytics debugger
                .build()
            Fabric.with(fabric)

        }
//        val fabric = Fabric.Builder(this)
//            .kits(Crashlytics())
//            .debuggable(BuildConfig.DEBUG) // Enables Crashlytics debugger
//            .build()
//        Fabric.with(fabric)
//        Fabric.with(this, Crashlytics())

        Fresco.initialize(this);
    }

    fun getConfigLocale(base: Context): Context {
        val locale = Locale(PreferenceUtils.getString(base, PreferenceUtils.KEY_LANGUAGE, Constants.VN_LOCALE))
        Locale.setDefault(locale)
        val config = base.resources.configuration
        config.setLocale(locale)
        return createConfigurationContext(config)
    }

}