package com.app.f49.model.dinhgia

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CamdoDTO : Serializable {
    var id: String? = null
    var stt: String? = null
    var name: String? = null

    @SerializedName("phone")
    var phoneNumber: String? = null
    var email: String? = null
    var active: Boolean? = null

    @SerializedName("regDate")
    var dateRegister: String? = null
    var brand: String? = null
    var image: String? = null
    var orders: String? = null

    var date: String? = null
    var asset: String? = null

    var balance: String? = null
    var description: String? = null

}