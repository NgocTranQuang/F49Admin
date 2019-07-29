package com.app.f49.fragment.profile

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.R
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.profile.UserProfileDTO
import com.app.f49.utils.GeneralUtils

class ProfileViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var userProfileDTO: MutableLiveData<UserProfileDTO> = MutableLiveData()
    var isLogoutSuccess: MutableLiveData<Boolean> = MutableLiveData()
    fun getProfile() {

        mApiService?.getProfile()?.checkRequest(mContext)?.subscribe({
            it?.let {
                it.get(0)?.let {
                    userProfileDTO?.value = it
                }
            }
        }, {

        }, {

        })
    }

    fun pushTokenFirebase(email: String?, token: String?, deviceName: String?, flg: Boolean) {
        if (email.isNullOrBlank()) {
            showDialogError(mContext.getString(R.string.provide_account))
            return
        }
        showLoading()
        mApiService?.pushFirebaseToken(email, token, GeneralUtils.getDeviceId(mContext) ?:"", flg).checkRequest(mContext)?.subscribe({
            isLogoutSuccess.value = true
        }, {
            showDialogError(it)
            isLogoutSuccess.value = false
        }, {
            hideLoading()
            isLogoutSuccess.value = false
        })
    }
}