package com.app.f49.activity.infoContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.infocontract.InfoContractDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator

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
}