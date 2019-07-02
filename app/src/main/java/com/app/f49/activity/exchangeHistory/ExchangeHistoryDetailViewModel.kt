package com.app.f49.activity.exchangeHistory

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.history.DetailExchangeDTO

class ExchangeHistoryDetailViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var detailExchange: MutableLiveData<DetailExchangeDTO> = MutableLiveData()
    fun getChiTietLichSuGiaoDich(idGiaoDich: Int?, idHopDong: Int?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getChiTietLichSuGiaoDich(idGiaoDich, idHopDong)) {
            detailExchange?.value = it
        }

    }

}