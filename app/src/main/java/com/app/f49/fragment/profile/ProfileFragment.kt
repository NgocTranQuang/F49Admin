package com.app.f49.fragment.profile

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.f49.Base
import com.app.f49.R
import com.app.f49.activity.changepassword.ChangePasswordActivity
import com.app.f49.activity.liststore.ListStoreActivity
import com.app.f49.activity.login.LoginActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.FragmentProfileBinding
import com.app.f49.fragment.base.BaseMvvmFragment
import com.app.f49.utils.GeneralUtils
import com.app.f49.utils.PreferenceUtils
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseMvvmFragment<FragmentProfileBinding, ProfileViewModel, BaseNavigator>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClickListenr()
        setData()
        observer()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_profile
    }
    private fun observer() {
//        viewBinding?.
        viewModel?.setNavigator(this)
        viewModel?.userProfileDTO?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                viewBinding?.item = it
            }
        })
        viewModel?.isLogoutSuccess?.observe(this, Observer {
            if (it == true) {
                Base.listStore.value?.clear()
                PreferenceUtils.writeBoolean(activity
                    ?: return@Observer, PreferenceUtils.KEY_IS_LOGOUT, true)
                var intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })
    }

    private fun setData() {
        viewModel?.getProfile()

    }

    private fun eventClickListenr() {
        btnStoreList.setOnSingleClickListener {
            ListStoreActivity.start(activity ?: return@setOnSingleClickListener)
        }
        btnChangePassWord.setOnSingleClickListener {
            ChangePasswordActivity.start(activity ?: return@setOnSingleClickListener)
        }
        btnLogout.setOnSingleClickListener {
            var email = PreferenceUtils.getString(activity
                ?: return@setOnSingleClickListener, PreferenceUtils.KEY_EMAIL, "")
            var token = PreferenceUtils.getString(activity
                ?: return@setOnSingleClickListener, PreferenceUtils.KEY_TOKEN_FIREBASE, "")
            viewModel?.pushTokenFirebase(email, token, GeneralUtils.getDeviceName(), false)

        }
    }
}