package com.app.f49.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.app.f49.MainActivity
import com.app.f49.activity.login.LoginActivity
import com.app.f49.utils.PreferenceUtils
import vn.com.ttc.ecommerce.activity.base.BaseActivity

class SplashActivity : BaseActivity() {
    private val DELAY_TIME: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            if (PreferenceUtils.getBoolean(this, PreferenceUtils.KEY_REMEMBER_LOGIN, false)) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, DELAY_TIME)
    }
}