package com.app.f49.model.store

import com.google.gson.annotations.SerializedName

class StoreDTO {
    var id: String? = null
    @SerializedName("tenCuaHang")
    var storeName: String? = null
    var isChoose: Boolean? = null
}