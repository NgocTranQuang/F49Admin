package com.app.f49.fragment.dashboard

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.home.ItemHomeDTO

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