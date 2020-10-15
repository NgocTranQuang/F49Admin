package com.app.f49.model.createcontractother

import com.google.gson.annotations.SerializedName
import java.util.*

class InfoContractOtherDTO {
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
    @SerializedName("SoTienLaiTraGop")
    var soTienLaiTraGop: String? = null

    @SerializedName("SoNgayVay")
    var soNgayVay: String? = null

    @SerializedName("SoNgayTrongKy")
    var soNgayTrongKy: String? = null

    @SerializedName("SoKyVay")
    var soKyVay: String? = null

    @SerializedName("NgayCatLai")
    var ngayCatLai: String? = null

    @SerializedName("CatLaiTruoc")
    var catLaiTruoc: Boolean? = null

    @SerializedName("SoTienCatLaiTruoc")
    var soTienCatLaiTruoc: String? = null

    @SerializedName("ThuPhiTruoc")
    var thuPhiTruoc: Boolean? = null

    @SerializedName("SoTienThuPhi")
    var soTienThuPhi: String? = null

    @SerializedName("SoTienKhachNhan")
    var soTienKhachNhan: String? = null

    @SerializedName("GhiChu")
    var ghiChu: String? = null
}