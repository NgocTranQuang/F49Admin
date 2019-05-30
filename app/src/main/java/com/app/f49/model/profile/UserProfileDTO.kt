package com.app.f49.model.profile

import com.google.gson.annotations.SerializedName
import java.util.*

class UserProfileDTO {
    var id: String? = null
    @SerializedName("hinh")
    var avatarURL: String? = null
    var taiKhoan: String? = null
    @SerializedName("hoTen")
    var fullName: String? = "--"
    var dateOfBirth: Date? = null
    var position: String? = "--"
    var email: String? = "--"
    var room: String? = "--"
    var store: String? = "--"
    @SerializedName("phanQuyen")
    var permission: String? = "--"
    var dienThoai: String? = null
    var diaChi: String? = null
    var idCuaHangMacDinh: String? = null

}