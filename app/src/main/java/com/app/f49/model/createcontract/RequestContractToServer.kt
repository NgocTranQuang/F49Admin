package com.app.f49.model.createcontract


import com.google.gson.annotations.SerializedName


class RequestContractToServer {
    @SerializedName("ThongTinHopDong")
    var thongTinHopDong: InfoContractCreateDTO? = null
    @SerializedName("DSTaiSanTheChap")
    var dSTaiSanTheChap: MutableList<BasePropertiesDTO>? = null
}