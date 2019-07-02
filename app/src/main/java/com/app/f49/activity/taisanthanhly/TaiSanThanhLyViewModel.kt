package com.app.f49.activity.taisanthanhly

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.taisanthanhly.NhomTaiSanDTO
import com.app.f49.model.taisanthanhly.TaiSanDTO
import com.app.f49.model.taisanthanhly.TenTaiSanDTO
import com.app.f49.model.taisanthanhly.TrangThaiTaiSanDTO

class TaiSanThanhLyViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listNhomTaiSan: MutableLiveData<MutableList<NhomTaiSanDTO>> = MutableLiveData()
    var listTenTaiSan: MutableLiveData<MutableList<TenTaiSanDTO>> = MutableLiveData()
    var listTrangThaiTaiSan: MutableLiveData<MutableList<TrangThaiTaiSanDTO>> = MutableLiveData()
    var listTaiSan: MutableLiveData<MutableList<TaiSanDTO>> = MutableLiveData()

    fun getListNhomTaiSan() {
        handleRequestService(mApiService?.getListNhomTaiSan()) {
            listNhomTaiSan.value = it
        }
    }

    fun getListTenTaiSan(idNhom: Int?) {
        handleRequestService(mApiService?.getListTenTaiSan(idNhom)) {
            listTenTaiSan.value = it
        }
    }

    fun getListTrangThaiTaiSan() {
        handleRequestService(mApiService?.getListTrangThaiTaiSan()) {
            listTrangThaiTaiSan.value = it
        }
    }

    fun getListTaiSanThanhLy(idNhomVatCamDo: Int?, idVatCamDo: Int?, trangThai: Int?) {
        showLoading()
        handleRequestService(mApiService?.getListTaiSan(idNhomVatCamDo, idVatCamDo, trangThai)) {
            listTaiSan.value = it
        }
    }
}