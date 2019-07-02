package com.app.f49.fragment.home

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.home.ItemHomeDTO

class HomeViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listItemHome: MutableLiveData<MutableList<ItemHomeDTO>> = MutableLiveData()
//    fun getListStore() {
//        showLoading()
//        mApiService?.getAllStore()?.checkRequest(mContext)?.subscribe({
//            listStore.tenTrangThai = it
//        }, {
//            showDialogError(it)
//        }, {
//            hideLoading()
//        })
//    }

    fun getListItemHome(idStore: String) {
        showLoading()
        mApiService?.getDashboard(idStore.toInt())?.checkRequest(mContext)?.subscribe({
            listItemHome?.value = it
        }, {
            showDialogError(it)
        }, {
            hideLoading()
        })
    }
}