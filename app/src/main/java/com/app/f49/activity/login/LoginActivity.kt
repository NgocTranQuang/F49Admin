package com.app.f49.activity.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import com.app.f49.MainActivity
import com.app.f49.R
import com.app.f49.utils.PreferenceUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
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
        initView()
        setEventClick()
        observer()
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("token", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                PreferenceUtils.writeString(this, PreferenceUtils.KEY_TOKEN_FIREBASE, token)
                // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
                Log.d("token", token)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }

    private fun initView() {
        var isRemember = PreferenceUtils.getBoolean(this, PreferenceUtils.KEY_REMEMBER_LOGIN, false)
        cbRemember.isChecked = isRemember
        if (isRemember) {
            mViewModel?.email?.value = PreferenceUtils.getString(this, PreferenceUtils.KEY_EMAIL, "")
            mViewModel?.password?.value = PreferenceUtils.getString(this, PreferenceUtils.KEY_PASSWORD, "")
        }
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
            mViewModel?.login(edtEmail.text.toString(), edtPassword.text.toString())
        }
    }
}