package com.app.f49.activity.managerContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.HopDongCamDoDTO
import com.app.f49.model.nguoiQLHD.NguoiQLHDDTO
import com.app.f49.model.tab.TabDTO

class ContractViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listTab: MutableLiveData<MutableList<TabDTO>> = MutableLiveData()
    var listStatusContract: MutableLiveData<MutableList<TabDTO>> = MutableLiveData()
    var listNguoiQLHDDTO: MutableLiveData<MutableList<NguoiQLHDDTO>> = MutableLiveData()
    var listHDCM: MutableLiveData<MutableList<HopDongCamDoDTO>> = MutableLiveData()
    fun getListTab() {
        showLoading()
        mApiService?.getListTab().checkRequest(mContext)?.subscribe({
            listTab.value = it
        }, {
            //            showDialogError(it)
        }, {
            //            hideLoading()
        })
    }

    fun getListStatusContract() {
        showLoading()
        mApiService?.getStatusContract().checkRequest(mContext)?.subscribe({
            listStatusContract.value = it
        }, {
            showDialogError(it)
        }, {
            //            hideLoading()
        })
    }

    fun getListHopDong(idCuaHang: Int?, loaiHD: Int?, idTab: String?, tuKhoa: String?, timChinhXac: Boolean, idNguoiQuanLyHD: Int?, idTrangThaiHD: String?) {
        showLoading()
        handleRequestService(mApiService?.getHopDongCamDo(idCuaHang, loaiHD, idTab, tuKhoa, false, idNguoiQuanLyHD, idTrangThaiHD)) {
            it?.let {
                listHDCM.value = it
            }
        }
    }


}