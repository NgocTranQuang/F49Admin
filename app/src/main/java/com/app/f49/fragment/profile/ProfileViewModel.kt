package com.app.f49.fragment.profile

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.profile.UserProfileDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

class ProfileViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var userProfileDTO: MutableLiveData<UserProfileDTO> = MutableLiveData()
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
}