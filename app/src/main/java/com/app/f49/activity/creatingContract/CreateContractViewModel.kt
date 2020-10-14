package com.app.f49.activity.creatingContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.createcontract.IDCuaHangDTO
import com.app.f49.model.createcontract.LoadTaoMoiDTO
import com.app.f49.model.createcontract.OutputTinhTienKhachNhanDTO
import com.app.f49.model.createcontract.TaiSanInHDDTO
import com.app.f49.model.createcontractother.InputTinhLaiPhi
import com.app.f49.model.createcontractother.InputTinhTienKhachNhanOtherDTO
import com.app.f49.model.createcontractother.LoadTaoMoiOtherDTO
import com.app.f49.model.createcontractother.OutputTinhLaiPhi
import java.util.*

class CreateContractViewModel(app:Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var dateVay: MutableLiveData<Date> = MutableLiveData()
    var taiSan: MutableLiveData<MutableList<TaiSanInHDDTO>> = MutableLiveData()
    var item: MutableLiveData<LoadTaoMoiOtherDTO> = MutableLiveData()
    var outputLai:MutableLiveData<OutputTinhLaiPhi> = MutableLiveData()
    var outputPhi:MutableLiveData<OutputTinhLaiPhi> = MutableLiveData()
    var tienNhan:MutableLiveData<OutputTinhTienKhachNhanDTO> = MutableLiveData()
    init {
        dateVay.value = Date()
    }
    fun layDanhSachTaiSanDNGD() {
        handleRequestServiceObject(mApiService.layDanhSachTaiSanDNGD()) {
            taiSan.value = it
        }
    }
    fun layDanhSachTaiSanTG() {
        handleRequestServiceObject(mApiService.layDanhSachTaiSanTG()) {
            taiSan.value = it
        }
    }
    fun loadTaoMoiDNGD(iDCuaHang: IDCuaHangDTO) {
        handleRequestServiceObject(mApiService.loadTaoMoiDNGD(iDCuaHang)) {
            item.value = it
        }
    }
    fun loadTaoMoiTG(iDCuaHang: IDCuaHangDTO) {
        handleRequestServiceObject(mApiService.loadTaoMoiTG(iDCuaHang)) {
            item.value = it
        }
    }
    fun tinhTienLaiDNGD(rq: InputTinhLaiPhi){
        handleRequestServiceObject(mApiService.tinhTienLaiDNGD(rq)){
            outputLai.value = it
        }
    }
    fun tinhTienLaiTG(rq: InputTinhLaiPhi){
        handleRequestServiceObject(mApiService.tinhTienLaiTG(rq)){
            outputLai.value = it
        }
    }

    fun tinhTienPhiDNGD(rq: InputTinhLaiPhi){
        handleRequestServiceObject(mApiService.tinhTienPhiDNGD(rq)){
            outputPhi.value = it
        }
    }
    fun tinhTienPhiTG(rq: InputTinhLaiPhi){
        handleRequestServiceObject(mApiService.tinhTienPhiTG(rq)){
            outputPhi.value = it
        }
    }

    fun tinhSoTienKhachNhanDNGD(rq: InputTinhTienKhachNhanOtherDTO){
        handleRequestServiceObject(mApiService.tinhSoTienKhachNhanDNGD(rq)){
            tienNhan.value = it
        }
    }
    fun tinhSoTienKhachNhanTG(rq: InputTinhTienKhachNhanOtherDTO){
        handleRequestServiceObject(mApiService.tinhSoTienKhachNhanTG(rq)){
            tienNhan.value = it
        }
    }
}