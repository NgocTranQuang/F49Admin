package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName

class PropertiesOtherDTO:BasePropertiesDTO() {
    @SerializedName("HangSanXuat")
    var hangSanXuat:String? = null
    @SerializedName("DongSanPham")
    var dongSanPham:String? = null

    @SerializedName("IMEI")
    var iMEI:String? = null
    @SerializedName("MauSac")
    var mauSac:String? = null
    @SerializedName("VGA")
    var vGA:String? = null
    @SerializedName("TinhTrang")
    var tinhTrang:String? = null
    @SerializedName("DinhGia")
    var dinhGia:String? = null
    @SerializedName("MatKhau")
    var matKhau:String? = null
}