package com.app.f49.activity.quanlythuchi

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.quanlythuchi.QuanLyThuChiDetailDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator

class QuanLyThuChiDetailViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var quanLyThuChiDetailDTO: MutableLiveData<QuanLyThuChiDetailDTO> = MutableLiveData()
    fun getDetailQuanLyThuChi(idItem: Int?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getDetailQuanLyThuChi(idItem)) {
            it?.let {
                it.getOrNull(0)?.let {
                    quanLyThuChiDetailDTO.value = it
                }
            }
        }

    }
}