package com.app.f49.activity.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.app.f49.MainActivity
import com.app.f49.R
import com.app.f49.utils.PreferenceUtils
import extension.setOnSingleClickListener
import extension.underLine
import kotlinx.android.synthetic.main.activity_login.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class LoginActivity : BaseMvvmActivity<com.app.f49.databinding.ActivityLoginBinding, LoginViewModel, BaseNavigator>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvForgotPassword.underLine()
        setEventClick()
        observer()
    }

    private fun observer() {
        mViewModel?.setNavigator(this)
        mViewModel?.isLoginSuccessfully?.observe(this, Observer {
            PreferenceUtils.writeBoolean(this, PreferenceUtils.KEY_REMEMBER_LOGIN, cbRemember.isChecked)
            MainActivity.start(this)
        })
    }


    private fun setEventClick() {
        cvLogin.setOnSingleClickListener {
            mViewModel?.login()
        }
    }
}