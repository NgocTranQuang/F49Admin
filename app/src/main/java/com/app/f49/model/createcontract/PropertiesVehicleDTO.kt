package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName

class PropertiesVehicleDTO :BasePropertiesDTO() {

    @SerializedName("HangSanXuat")
    var hangSanXuat:String? = null
    @SerializedName("DongSanPham")
    var dongSanPham:String? = null
    @SerializedName("BienSoXe")
    var bienSoXe:String? = null
    @SerializedName("SoKhung")
    var soKhung:String? = null
    @SerializedName("SoMay")
    var soMay:String? = null
    @SerializedName("DungTich")
    var dungTich:String? = null
    @SerializedName("MauSac")
    var mauSac:String? = null
    @SerializedName("TinhTrang")
    var tinhTrang:String? = null
    @SerializedName("SoChoNgoi")
    var soChoNgoi:String? = null
    @SerializedName("ModelCode")
    var modelCode:String? = null
    @SerializedName("DinhGia")
    var dinhGia:String? = null
    @SerializedName("DangKy")
    var dangKy:String? = null
    @SerializedName("TenDangKy")
    var tenDangKy:String? = null
    @SerializedName("CoChiaKhoa")
    var coChiaKhoa:Boolean? = false
    @SerializedName("XKXKCC")
    var xKXKCC:Boolean? = false
}