package com.app.f49.activity.timkiem

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.timkiem.TimKiemDTO

class TimKiemViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listHopDong: MutableLiveData<MutableList<TimKiemDTO>> = MutableLiveData()

    fun timKiemHopDong(keySearch: String?, pageIndex: Int) {
        if (pageIndex == 1)
            showLoading()
        handleRequestServiceObject(mApiService?.timKiemHopDong(keySearch, pageIndex)) {
            it?.let {
                listHopDong?.value = it
            }
        }

    }
}