package com.app.f49

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.store.StoreDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

class MainViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listStore: MutableLiveData<MutableList<StoreDTO>> = MutableLiveData()
    var currentPositionStore: MutableLiveData<Int> = MutableLiveData()

    init {
        currentPositionStore.value = -1
    }

    fun getListStore() {
        showLoading()
        mApiService?.getAllStore()?.checkRequest(mContext)?.subscribe({
            listStore.value = it
        }, {
            showDialogError(it)
        }, {
            hideLoading()
        })
    }
}