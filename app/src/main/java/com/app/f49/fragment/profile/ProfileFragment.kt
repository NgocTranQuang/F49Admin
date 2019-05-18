package com.app.f49.fragment.profile

import android.os.Bundle
import android.view.View
import com.app.f49.activity.changepassword.ChangePasswordActivity
import com.app.f49.activity.liststore.ListStoreActivity
import com.app.f49.databinding.FragmentProfileBinding
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_profile.*
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.base.BaseMvvmFragment

class ProfileFragment : BaseMvvmFragment<FragmentProfileBinding, ProfileViewModel, BaseNavigator>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClickListenr()
    }

    private fun eventClickListenr() {
        btnStoreList.setOnSingleClickListener {
            ListStoreActivity.start(activity ?: return@setOnSingleClickListener)
        }
        btnChangePassWord.setOnSingleClickListener {
            ChangePasswordActivity.start(activity ?: return@setOnSingleClickListener)
        }
    }
}