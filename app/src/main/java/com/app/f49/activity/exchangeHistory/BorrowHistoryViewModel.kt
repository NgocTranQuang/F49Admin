package com.app.f49.activity.exchangeHistory

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.history.DetailBorrowDTO

class BorrowHistoryViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var detailBorrowDTO: MutableLiveData<DetailBorrowDTO> = MutableLiveData()
    fun getChiTietLichSuVayNo(idHopDong: Int?) {
        handleRequestServiceObject(mApiService?.getChiTietLichSuVayNo(idHopDong)) {
            detailBorrowDTO?.value = it
        }
    }
}