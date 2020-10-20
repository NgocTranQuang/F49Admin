package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName
import java.util.*

class InfoContractCreateDTO {
    @SerializedName("IDCuaHang")
    var iDCuaHang: String? = null
    @SerializedName("IDKhachHang")
    var iDKhachHang: String? = null
    @SerializedName("NgayVay")
    var ngayVay: String? = null
    @SerializedName("NgayVaoSo")
    var ngayVaoSo: String? = null
    @SerializedName("SoTienVay")
    var soTienVay: String? = null
    @SerializedName("LaiXuat")
    var laiXuat: String? = null
    @SerializedName("SoNgayVay")
    var soNgayVay: String? = null
    @SerializedName("NgayCatLai")
    var ngayCatLai: String? = null
    @SerializedName("CatLaiTruoc")
    var catLaiTruoc: Boolean? = null
    @SerializedName("SoTienCatLaiTruoc")
    var soTienCatLaiTruoc: String? = null
    @SerializedName("SoTienThuPhi")
    var soTienThuPhi: String? = null
    @SerializedName("SoTienKhachNhan")
    var soTienKhachNhan: String? = null
    @SerializedName("GhiChu")
    var ghiChu: String? = null

}