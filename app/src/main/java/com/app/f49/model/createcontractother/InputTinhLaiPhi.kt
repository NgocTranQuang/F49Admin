package com.app.f49.model.createcontractother

import com.google.gson.annotations.SerializedName

class InputTinhLaiPhi {
    @SerializedName("SoTienVay")
    var soTienVay :String? = null
    @SerializedName("PhanTramPhi")
    var phanTramPhi:String? =null
    @SerializedName("PhanTramLai")
    var phanTramLai:String? =null
    @SerializedName("SoNgayVay")
    var soNgayVay:String? =null
}