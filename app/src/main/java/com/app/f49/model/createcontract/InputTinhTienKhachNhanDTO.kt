package com.app.f49.model.createcontract

import com.google.gson.annotations.SerializedName

class InputTinhTienKhachNhanDTO {
    @SerializedName("TenVatCamCo")
    var tenVatCamCo :String? = null
    @SerializedName("IDVatCamCo")
    var iDVatCamCo :String? = null
    @SerializedName("SoTienVay")
    var soTienVay :String? = null
    @SerializedName("LaiXuat")
    var laiXuat:String? = null
    @SerializedName("SoNgayVay")
    var soNgayVay:String? =null
    @SerializedName("CatLaiTruoc")
    var catLaiTruoc:Boolean? = null
    @SerializedName("TienThuPhi")
    var tienThuPhi:String? = null

}
