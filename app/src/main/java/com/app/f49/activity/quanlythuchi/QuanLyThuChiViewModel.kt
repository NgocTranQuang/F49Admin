package com.app.f49.activity.quanlythuchi

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.toStringISO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
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

        handleRequestService(mApiService?.getDataQuanLyThuChi(idStore, fromDate?.value?.toStringISO(), toDate.value?.toStringISO())) {
            it?.let {
                listQuanLyThuChi?.value = it
            }
        }

    }
    fun getListVonDauTu(idStore: Int?) {

        showLoading()

        handleRequestService(mApiService?.getListVonDauTu(idStore, fromDate?.value?.toStringISO(), toDate.value?.toStringISO())) {
            it?.let {
                listQuanLyThuChi?.value = it
            }
        }

    }
}