package com.app.f49

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.store.StoreDTO
import com.app.f49.model.topmenu.TopMenuDTO
import com.app.f49.utils.Constants

class MainViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listStore: MutableLiveData<MutableList<StoreDTO>> = MutableLiveData()
    var currentPositionStore: MutableLiveData<Int> = MutableLiveData()
    var topMenu: MutableLiveData<TopMenuDTO> = MutableLiveData()
    var notificationCount: MutableLiveData<Int> = MutableLiveData()

    init {
        currentPositionStore.value = -1
    }

    fun getListStore() {
        showLoading()
        mApiService?.getAllStore()?.checkRequest(mContext)?.subscribe({
            Base.listStore.value = it
            listStore.value = it
        }, {
            showDialogError(it)
        }, {
            hideLoading()
        })
    }

    fun getTopMenu() {
        showLoading()
        mApiService?.getTopMenu()?.checkRequest(mContext)?.subscribe({
            topMenu.value = it
        }, {
            showDialogError(it)
        }, {
            hideLoading()
        })
    }

    fun getCountNotificationUnread(idCuaHang: Int?) {
        mApiService?.getCountNotificationUnread(idCuaHang)?.checkRequest(mContext)?.subscribe {
            notificationCount.value = it?.countUnread
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