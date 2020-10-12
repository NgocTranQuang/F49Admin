package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName

class PropertiesLaptopDTO :BasePropertiesDTO() {

    @SerializedName("HangSanXuat")
    var hangSanXuat:String? = null
    @SerializedName("DongSanPham")
    var dongSanPham:String? = null
    @SerializedName("CPU")
    var cPU:String? = null
    @SerializedName("RAM")
    var rAM:String? = null
    @SerializedName("HDD")
    var hDD:String? = null
    @SerializedName("MauSac")
    var mauSac:String? = null
    @SerializedName("TinhTrang")
    var tinhTrang:String? = null
    @SerializedName("ManHinh")
    var manHinh:String? = null
    @SerializedName("VGA")
    var vGA:String? = null
    @SerializedName("DinhGia")
    var dinhGia:String? = null
    @SerializedName("MatKhau")
    var matKhau:String? = null
}