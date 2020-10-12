package com.app.f49.activity.creatingContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.createcontract.*

class KhachHangViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var khachHang: MutableLiveData<MutableList<KhachHangDTO>> = MutableLiveData()
    var taiSan: MutableLiveData<MutableList<TaiSanInHDDTO>> = MutableLiveData()
    var thuocTinh: MutableLiveData<MutableList<ThuocTinhTaiSanDTO>> = MutableLiveData()
    var item: MutableLiveData<LoadTaoMoiDTO> = MutableLiveData()
    var output:MutableLiveData<OutputTinhTienKhachNhanDTO> = MutableLiveData()
    var result:MutableLiveData<ResultContractDTO> = MutableLiveData()
    fun timKiem(key: String?) {
        handleRequestServiceObject(mApiService.timKiem(key)) {
            khachHang.value = it
        }
    }

    fun layDanhSachTaiSan() {
        handleRequestServiceObject(mApiService.layDanhSachTaiSan()) {
            taiSan.value = it
        }
    }

    fun layThuocTinhTaiSan(loaiTaiSan: String) {
        handleRequestServiceObject(mApiService.layThuocTinhTaiSan(loaiTaiSan)) {
            thuocTinh.value = it
        }
    }

    fun loadTaoMoi(iDCuaHang: IDCuaHangDTO) {
        handleRequestServiceObject(mApiService.loadTaoMoi(iDCuaHang)) {
            item.value = it
        }
    }

    fun tinhSoTienKhachNhan(rq: InputTinhTienKhachNhanDTO) {
        handleRequestServiceObject(mApiService.tinhSoTienKhachNhan(rq)) {
            output.value = it
        }
    }
    fun luuHopDong(rq:RequestContractToServer){
        handleRequestServiceObject(mApiService.luuHopDong(rq)){
            result.value = it
        }

    }
}