package com.app.f49.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.app.f49.MainActivity
import com.app.f49.activity.login.LoginActivity
import vn.com.ttc.ecommerce.activity.base.BaseActivity

class SplashActivity : BaseActivity() {
    private val DELAY_TIME: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, DELAY_TIME)
    }
}