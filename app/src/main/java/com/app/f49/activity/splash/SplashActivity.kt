package com.app.f49.activity.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.app.f49.MainActivity
import com.app.f49.R
import com.app.f49.activity.base.BaseActivity
import com.app.f49.activity.login.LoginActivity
import com.app.f49.activity.login.LoginViewModel
import com.app.f49.utils.PreferenceUtils

class SplashActivity : BaseActivity() {
    private val DELAY_TIME: Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if (!PreferenceUtils.getBoolean(this, PreferenceUtils.KEY_ALREADY_INSTALL_APP, false)) {
                PreferenceUtils.writeBoolean(this, PreferenceUtils.KEY_ALREADY_INSTALL_APP, true)
                startLoginActivity()
                return@postDelayed
            }
            if (!PreferenceUtils.getBoolean(this, PreferenceUtils.KEY_IS_LOGOUT, true)) {
                if (PreferenceUtils.getBoolean(this, PreferenceUtils.KEY_REMEMBER_LOGIN, false)) {
                    var viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
                    var email = PreferenceUtils.getString(this, PreferenceUtils.KEY_EMAIL, "") ?: ""
                    var password = PreferenceUtils.getString(this, PreferenceUtils.KEY_PASSWORD, "") ?: ""
                    viewModel.login(email, password)
                    viewModel?.isLoginSuccessfully?.observe(this, Observer {
                        if (it == true) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            startLoginActivity()
                        }
                    })
                } else {
                    startLoginActivity()
                }
            } else {
                startLoginActivity()
            }
        }, DELAY_TIME)
    }

    fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}