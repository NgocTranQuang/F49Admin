package com.app.f49

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.app.f49.utils.Constants
import com.app.f49.utils.PreferenceUtils
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
        }
//        Fresco.initialize(this);
    }

    fun getConfigLocale(base: Context): Context {
        val locale = Locale(PreferenceUtils.getString(base, PreferenceUtils.KEY_LANGUAGE, Constants.VN_LOCALE))
        Locale.setDefault(locale)
        val config = base.resources.configuration
        config.setLocale(locale)
        return createConfigurationContext(config)
    }

}