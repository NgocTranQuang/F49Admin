package com.app.f49.activity.rutlaicuahang

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.rutlai.RutLaiDTO
import com.app.f49.model.tab.TabDTO

class RutLaiCuaHangViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listTab: MutableLiveData<MutableList<TabDTO>> = MutableLiveData()
    var listDataHopDong: MutableLiveData<MutableList<RutLaiDTO>> = MutableLiveData()
    fun getListTab() {
        showLoading()
        handleRequestService(mApiService?.getTabRutLaiCuahang()) {
            it?.let {
                listTab.value = it
            }
        }
    }

    fun getListRutLai(idCuaHang: Int?, idTab: Int? = 0) {
        showLoading()
        handleRequestService(mApiService?.getDataRutLai(idCuaHang, idTab)) {
            it?.let {
                listDataHopDong?.value = it
            }
        }
    }

    fun getListRutVon(idCuaHang: Int?, idTab: Int? = 0) {
        showLoading()
        handleRequestService(mApiService?.getListRutVon(idCuaHang, idTab)) {
            it?.let {
                listDataHopDong?.value = it
            }
        }
    }

}