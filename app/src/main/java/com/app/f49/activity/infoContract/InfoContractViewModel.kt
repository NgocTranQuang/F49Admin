package com.app.f49.activity.infoContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.infocontract.InfoContractDTO

class InfoContractViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var infoContract: MutableLiveData<InfoContractDTO> = MutableLiveData()
    fun getChiTietHDCD(id: String) {
        showLoading()
        handleRequestService(mApiService.getChiTietHDCD(id?.toIntOrNull())) {
            it?.getOrNull(0)?.let {
                infoContract.value = it
            }
        }
    }
    fun getChiTietHDGiaDung(id: String) {
        showLoading()
        handleRequestService(mApiService.getChiTietCamDoGiaDung(id?.toIntOrNull())) {
            it?.getOrNull(0)?.let {
                infoContract.value = it
            }
        }
    }
    fun getChiTietCamDoTraGop(id: String) {
        showLoading()
        handleRequestService(mApiService.getChiTietHopDongTraGop(id?.toIntOrNull())) {
            it?.getOrNull(0)?.let {
                infoContract.value = it
            }
        }
    }
    fun getChiTietDuNoGiamDan(id: String) {
        showLoading()
        handleRequestService(mApiService.getChiTietHopDongDuNoGiamDan(id?.toIntOrNull())) {
            it?.getOrNull(0)?.let {
                infoContract.value = it
            }
        }
    }
}