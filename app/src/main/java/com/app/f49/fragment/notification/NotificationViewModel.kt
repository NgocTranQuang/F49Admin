package com.app.f49.fragment.notification

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.notification.NotificationDTO
import com.app.f49.utils.Constants
import com.app.f49.utils.PreferenceUtils
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

class NotificationViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listNotification: MutableLiveData<MutableList<NotificationDTO>> = MutableLiveData()
    var changeNotificationSuccessfully: MutableLiveData<Boolean> = MutableLiveData()
    fun getListNotification(idStore : String?) {
        var email = PreferenceUtils.getString(mContext, PreferenceUtils.KEY_EMAIL, "")
        email?.let {
            showLoading()
            handleRequestService(mApiService?.getNotificationList(idStore,it)) {
                listNotification.value = it
            }

        }
    }

    fun changeStatusNotificationToRead(idNotification: Int?, finished: (Boolean) -> Unit) {

        mApiService?.changeReadNotification(idNotification).checkRequest(mContext)?.subscribe({
            finished.invoke(true)
        }, {
            if (it.message?.contains(Constants.KEY_ITEM_NULL) == true) {
                finished.invoke(true)
                hideLoading()
            } else {
                finished.invoke(false)
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }

}