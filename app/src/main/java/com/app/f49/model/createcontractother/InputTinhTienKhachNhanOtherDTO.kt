package com.app.f49.model.createcontractother

import com.google.gson.annotations.SerializedName

class InputTinhTienKhachNhanOtherDTO {

    @SerializedName("SoTienVay")
    var soTienVay :String? = null

    @SerializedName("SoNgayVay")
    var soNgayVay:String? =null

    @SerializedName("SoNgayTrongKy")
    var soNgayTrongKy:String? = null

    @SerializedName("SoTienLai")
    var soTienLai:String? = null

    @SerializedName("TienThuPhi")
    var tienThuPhi:String? = null

    @SerializedName("CatLaiTruoc")
    var catLaiTruoc:Boolean? = null

    @SerializedName("ThuPhiTruoc")
    var thuPhiTruoc:Boolean? = null
}
