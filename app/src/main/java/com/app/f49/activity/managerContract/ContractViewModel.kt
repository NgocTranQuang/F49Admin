package com.app.f49.activity.managerContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.HopDongCamDoDTO
import com.app.f49.model.nguoiQLHD.NguoiQLHDDTO
import com.app.f49.model.tab.TabDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

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

    fun getListNguoiQLHD(idStore: String) {
        mApiService?.getNguoiQLHD(idStore).checkRequest(mContext)?.subscribe({
            it?.let {
                listNguoiQLHDDTO.value = it
            }
        }, {
            //            showDialogError(it)false
        }, {
            hideLoading()
        })
    }

    fun getHopDongCamDo(idCuaHang: Int?, idTab: String?, tuKhoa: String?, timChinhXac: Boolean, idNguoiQuanLyHD: Int?, idTrangThaiHD: String?) {
        showLoading()
        handleRequestService(mApiService?.getHopDongCamDo(idCuaHang, idTab, tuKhoa, false, idNguoiQuanLyHD, idTrangThaiHD)) {
            it?.let {
                listHDCM.value = it
            }
        }
    }


    fun getCamDoGiaDung(idCuaHang: Int?, idTab: String?, tuKhoa: String?, timChinhXac: Boolean, idNguoiQuanLyHD: Int?, idTrangThaiHD: String?) {
        showLoading()
        handleRequestService(mApiService?.getCamdoGiaDung(idCuaHang, idTab, tuKhoa, false, idNguoiQuanLyHD, idTrangThaiHD)) {
            it?.let {
                listHDCM.value = it
            }
        }
    }


    fun getHopDongTraGop(idCuaHang: Int?, idTab: String?, tuKhoa: String?, timChinhXac: Boolean, idNguoiQuanLyHD: Int?, idTrangThaiHD: String?) {
        showLoading()
        handleRequestService(mApiService?.getHopDongTraGop(idCuaHang, idTab, tuKhoa, false, idNguoiQuanLyHD, idTrangThaiHD)) {
            it?.let {
                listHDCM.value = it
            }
        }
    }


}