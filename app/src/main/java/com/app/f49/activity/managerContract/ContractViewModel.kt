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

    fun getHDCM(idCuaHang: Int?, idTab: String?, tuKhoa: String?, timChinhXac: Boolean, idNguoiQuanLyHD: Int?, idTrangThaiHD: String?) {
        showLoading()
        handleRequestService(mApiService?.getHDCM(idCuaHang, idTab, tuKhoa,false , idNguoiQuanLyHD, idTrangThaiHD)) {
            it?.let {
                listHDCM.value = it
            }
        }


//        mApiService?.getHDCM(idCuaHang, trangThai, tuKhoa, timChinhXac, idNguoiQuanLyHD, thoiGian).checkRequest(mContext)?.subscribe({
//            it?.let {
//                listHDCM.value = it
//            }
//        }, {
//            showDialogError(it)
//        }, {
//            hideLoading()
//        })
    }


}