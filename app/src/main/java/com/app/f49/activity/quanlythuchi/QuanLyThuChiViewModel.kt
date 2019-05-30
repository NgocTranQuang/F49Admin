package com.app.f49.activity.quanlythuchi

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import java.util.*

class QuanLyThuChiViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var fromDate: MutableLiveData<Date> = MutableLiveData()
    var toDate: MutableLiveData<Date> = MutableLiveData()
    var clickable: MutableLiveData<Boolean> = MutableLiveData()
    var listQuanLyThuChi: MutableLiveData<MutableList<QuanLyThuChiDTO>> = MutableLiveData()

    init {
        fromDate.value = Date()
        toDate.value = Date()
    }

    fun getListQuanLyThuChi(idStore: Int?) {
        showLoading()
        handleRequestService(mApiService?.getDataQuanLyThuChi(idStore, fromDate.value, toDate.value)) {
            it?.let {
                listQuanLyThuChi?.value = it
            }
        }

    }
}