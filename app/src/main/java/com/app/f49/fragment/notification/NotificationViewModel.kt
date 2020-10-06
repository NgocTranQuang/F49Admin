package com.app.f49.fragment.notification

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.notification.NotificationDTO
import com.app.f49.utils.Constants

class NotificationViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listNotification: MutableLiveData<MutableList<NotificationDTO>> = MutableLiveData()
    var putReadAll: MutableLiveData<Boolean> = MutableLiveData()
    fun getListNotification(idStore: String?, pageIndex: Int?) {
        if (pageIndex == 0) {
            showLoading()
        }
        handleRequestService(mApiService?.getNotificationList(idStore?.toIntOrNull(), pageIndex,pageSize)) {
            listNotification.value = it
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

    fun putReadAll(idCuaHang: Int?) {
        mApiService?.putReadAll(idCuaHang).checkRequest(mContext)?.subscribe({
            putReadAll.value = true
        }, {
            if (it.message?.contains(Constants.KEY_ITEM_NULL) == true) {
                putReadAll.value = true
                hideLoading()
            } else {
                putReadAll.value = false
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }

    fun deleteNotification(id: Int?, finished: (Boolean) -> Unit) {
        handleRequestServiceObject(mApiService.deleteNotification(id)) {
            finished.invoke(true)
        }
    }
}