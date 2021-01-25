package com.app.f49.activity.creatingContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.createcontract.*
import com.app.f49.model.createcontractother.*
import java.util.*

class KhachHangViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var dateVay: MutableLiveData<Date> = MutableLiveData()
    var khachHang: MutableLiveData<MutableList<KhachHangDTO>> = MutableLiveData()
    var newKhachHang: MutableLiveData<KhachHangDTO> = MutableLiveData()
    var taiSan: MutableLiveData<MutableList<TaiSanInHDDTO>> = MutableLiveData()
    var thuocTinh: MutableLiveData<MutableList<ThuocTinhTaiSanDTO>> = MutableLiveData()
    var item: MutableLiveData<LoadTaoMoiDTO> = MutableLiveData()
    var tienNhan:MutableLiveData<OutputTinhTienKhachNhanDTO> = MutableLiveData()
    var item2: MutableLiveData<LoadTaoMoiOtherDTO> = MutableLiveData()
    var outputLai:MutableLiveData<OutputTinhLaiPhi> = MutableLiveData()
    var outputPhi:MutableLiveData<OutputTinhLaiPhi> = MutableLiveData()
    var soHD:MutableLiveData<ResultContractDTO> = MutableLiveData()
    init {
        dateVay.value = Date()
    }
    fun timKiem(key: String?) {
        handleRequestServiceObject(mApiService.timKiem(key)) {
            khachHang.value = it
        }
    }

    fun luuKhachHang(rq : KhachHangDTO){
        handleRequestServiceObject(mApiService.luuKhachHang(rq)){
            newKhachHang.value = it
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
            tienNhan.value = it
        }
    }
    fun luuHopDong(rq:RequestContractToServer){
        handleRequestServiceObject(mApiService.luuHopDong(rq)){
            soHD.value = it
        }

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
            item2.value = it
        }
    }
    fun loadTaoMoiTG(iDCuaHang: IDCuaHangDTO) {
        handleRequestServiceObject(mApiService.loadTaoMoiTG(iDCuaHang)) {
            item2.value = it
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
    fun luuHopDongTG(rq: RequestOtherContractToServer){
        handleRequestServiceObject(mApiService.luuHopDongTG(rq)){
            soHD.value = it
        }

    }
}