package com.app.f49.activity.changepassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_change_password.*
import vn.com.ttc.ecommerce.activity.base.BaseActivity

class ChangePasswordActivity : BaseActivity() {
    companion object {
        fun start(context: Context){
            context.startActivity(Intent(context,ChangePasswordActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        configView()
        evenClick()
    }

    private fun evenClick() {
        cvOk.setOnSingleClickListener {
            showToastErrorMsg("Change thanh cmn cong")
        }
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.change_pass_word)
    }
}