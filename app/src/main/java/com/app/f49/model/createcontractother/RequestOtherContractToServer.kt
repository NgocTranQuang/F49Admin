package com.app.f49.model.createcontractother


import com.google.gson.annotations.SerializedName


class RequestOtherContractToServer {
    @SerializedName("ThongTinHopDong")
    var thongTinHopDong: InfoContractOtherDTO? = null
    @SerializedName("DSTaiSanTheChap")
    var dSTaiSanTheChap: MutableList<PropertiesCollateralDTO>? = null
}