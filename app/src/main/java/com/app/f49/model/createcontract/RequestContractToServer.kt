package com.app.f49.model.createcontract


import com.google.gson.annotations.SerializedName
import java.util.*

class RequestContractToServer {
    @SerializedName("ThongTinHopDong")
    var thongTinHopDong: InfoContractCreateDTO? = null
    @SerializedName("DSTaiSanTheChap")
    var dSTaiSanTheChap: MutableList<BasePropertiesDTO>? = null
    @SerializedName("NgayVay")
    var ngayVay: Date? = null
    @SerializedName("NgayVaoSo")
    var ngayVaoSo: Date? = null
    @SerializedName("SoTienVay")
    var soTienVay :String? = null
    @SerializedName("LaiXuat")
    var laiXuat:String? = null
    @SerializedName("SoNgayVay")
    var soNgayVay:String? =null
    @SerializedName("NgayCatLai")
    var ngayCatLai: Date? = null
    @SerializedName("CatLaiTruoc")
    var catLaiTruoc:Boolean? = false
    @SerializedName("SoTienCatLaiTruoc")
    var soTienCatLaiTruoc: String? = null
    @SerializedName("SoTienThuPhi")
    var soTienThuPhi:String? = null
    @SerializedName("SoTienKhachNhan")
    var soTienKhachNhan: String? = null
    @SerializedName("GhiChu")
    var ghiChu: String? = null
}