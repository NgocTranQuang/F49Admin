package com.app.f49.activity.exchangeHistory

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.history.BorrowHistoryDTO
import com.app.f49.model.history.ExchangeHistoryDTO

class ExchangeHistoryViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var soHopDong : MutableLiveData<String> = MutableLiveData()
    var listExchangeHistory: MutableLiveData<MutableList<ExchangeHistoryDTO>> = MutableLiveData()
    var listBorrowHistory: MutableLiveData<MutableList<BorrowHistoryDTO>> = MutableLiveData()

    fun getListLichSuGiaoDich(idHopDong: Int?) {
        handleRequestService(mApiService?.getLichSuGiaoDich(idHopDong)) {
            listExchangeHistory?.value = it
        }
    }
    fun getListLichSuVayNo(idKH: Int?) {
        handleRequestService(mApiService?.getLichSuVayNo(idKH)) {
            listBorrowHistory?.value = it
        }
    }
}