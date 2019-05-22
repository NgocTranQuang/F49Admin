package com.app.f49.fragment.home

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.home.ItemHomeDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

class HomeViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listItemHome: MutableLiveData<MutableList<ItemHomeDTO>> = MutableLiveData()
//    fun getListStore() {
//        showLoading()
//        mApiService?.getAllStore()?.checkRequest(mContext)?.subscribe({
//            listStore.value = it
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