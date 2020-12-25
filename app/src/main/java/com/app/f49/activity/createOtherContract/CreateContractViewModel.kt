package com.app.f49.activity.createOtherContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.createcontract.*
import com.app.f49.model.createcontractother.*
import java.util.*

class CreateContractViewModel(app:Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var dateVay: MutableLiveData<Date> = MutableLiveData()
    var taiSan: MutableLiveData<MutableList<TaiSanInHDDTO>> = MutableLiveData()
    var item: MutableLiveData<LoadTaoMoiOtherDTO> = MutableLiveData()
    var outputLai:MutableLiveData<OutputTinhLaiPhi> = MutableLiveData()
    var outputPhi:MutableLiveData<OutputTinhLaiPhi> = MutableLiveData()
    var tienNhan:MutableLiveData<OutputTinhTienKhachNhanDTO> = MutableLiveData()
    var soHD:MutableLiveData<ResultContractDTO> = MutableLiveData()
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

    fun luuHopDongGDTG(rq: RequestOtherContractToServer){
        handleRequestServiceObject(mApiService.luuHopDongGDTG(rq)){
            soHD.value = it
        }

    }
    fun luuHopDongTG(rq:RequestOtherContractToServer){
        handleRequestServiceObject(mApiService.luuHopDongTG(rq)){
            soHD.value = it
        }

    }
}