package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName

open class BasePropertiesDTO {

    @SerializedName("TenVatCamCo")
    var tenVatCamCo :String? = null
    @SerializedName("IDVatCamCo")
    var iDVatCamCo :String? = null
    @SerializedName("HangSanXuat")
    var hangSanXuat:String? = null
    @SerializedName("DongSanPham")
    var dongSanPham:String? = null
    @SerializedName("HinhAnh")
    var hinhAnh:String? = null
}