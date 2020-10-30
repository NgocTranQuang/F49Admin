package com.app.f49.model.createcontractother

import com.app.f49.model.createcontract.PropertiesImageDTO
import com.google.gson.annotations.SerializedName

open class PropertiesCollateralDTO {
    @SerializedName("TenVatCamCo")
    var tenVatCamCo :String? = null

    @SerializedName("IDVatCamCo")
    var iDVatCamCo :String? = null

    @SerializedName("MoTa")
    var moTa :String? = null

    @SerializedName("ViTriDeDo")
    var viTriDeDo :String? = null

    @SerializedName("GiayToKemTheo")
    var giayToKemTheo :String? = null

    @SerializedName("DinhGia")
    var dinhGia :String? = null

    @SerializedName("HinhAnh")
    var hinhAnh:MutableList<PropertiesImageDTO>? = null
}