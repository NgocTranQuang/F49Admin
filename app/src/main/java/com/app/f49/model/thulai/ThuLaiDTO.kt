package com.app.f49.model.thulai

import com.google.gson.annotations.SerializedName
import java.util.*

class ThuLaiDTO {
    var id: Int? = null
    @SerializedName("soHopDong")
    var soHD: String? = null
    @SerializedName("tenKhachHang")
    var tenKH: String? = null
    var soTienVay: Double? = null
    var noGoc: Double? = null
    var noLai: Double? = null
    var phaiThu: Double? = null
    var idCuaHang: Int? = null
    var tenCuaHang: String? = null
    var ngayHieuLuc: Date? = null

}