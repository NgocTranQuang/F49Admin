package com.app.f49.fragment.dashboard

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.home.ItemHomeDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

class DashBoardViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {


    var listItemDashBoard: MutableLiveData<MutableList<ItemHomeDTO>> = MutableLiveData()

    fun getListItemHome(s: String) {
        showLoading()
        mApiService?.getTienIch(s.toInt())?.checkRequest(mContext)?.subscribe({
            it?.let {
                listItemDashBoard?.value = it
            }
        }, {
            showDialogError(it)
        }, {
            hideLoading()
        })

    }

}