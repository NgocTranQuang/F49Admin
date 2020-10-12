package com.app.f49.activity.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import com.app.f49.BuildConfig
import com.app.f49.MainActivity
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.utils.GeneralUtils
import com.app.f49.utils.PreferenceUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.app.f49.extension.setOnSingleClickListener
import com.app.f49.extension.underLine
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMvvmActivity<com.app.f49.databinding.ActivityLoginBinding, LoginViewModel, BaseNavigator>() {

    var tokenFireBase: String = ""
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
                tokenFireBase = token ?: ""
                PreferenceUtils.writeString(this, PreferenceUtils.KEY_TOKEN_FIREBASE, token)
                Log.d("token", token)
            })
    }

    private fun initView() {
        mViewBinding?.viewModel = mViewModel
        var isRemember = PreferenceUtils.getBoolean(this, PreferenceUtils.KEY_REMEMBER_LOGIN, false)
        cbRemember.isChecked = isRemember
        if (isRemember) {
            mViewModel?.email?.value = PreferenceUtils.getString(this, PreferenceUtils.KEY_EMAIL, "")
            mViewModel?.password?.value = PreferenceUtils.getString(this, PreferenceUtils.KEY_PASSWORD, "")
        }
        mViewModel?.version?.value = "F49 version ${BuildConfig.VERSION_NAME}"
    }

    private fun observer() {
        mViewModel?.setNavigator(this)
        mViewModel?.isLoginSuccessfully?.observe(this, Observer {
            if (it == true) {
                    if (!tokenFireBase.isNullOrBlank()) {
                        mViewModel?.pushTokenFirebase(edtEmail.text.toString(), tokenFireBase, GeneralUtils.getDeviceId(this) ?:"", true)
                    }
                PreferenceUtils.writeBoolean(this, PreferenceUtils.KEY_REMEMBER_LOGIN, cbRemember.isChecked)
                MainActivity.start(this)
            }
        })
    }


    private fun setEventClick() {
        cvLogin.setOnSingleClickListener {
            if (edtEmail.text.isNullOrBlank() || edtPassword.text.isNullOrBlank()) {
                showErrorDialog(getString(R.string.provide_account))
                return@setOnSingleClickListener
            }
            mViewModel?.login(edtEmail.text.toString(), edtPassword.text.toString())

        }
    }
}