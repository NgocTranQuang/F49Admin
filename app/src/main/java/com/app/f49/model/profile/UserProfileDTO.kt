package com.app.f49.model.profile

import com.google.gson.annotations.SerializedName
import java.util.*

class UserProfileDTO {
    var id: String? = null
    @SerializedName("hinh")
    var avatarURL: String? = null
    var taiKhoan: String? = null
    @SerializedName("hoTen")
    var fullName: String? = null
    var dateOfBirth: Date? = null
    var position: String? = null
    var email: String? = null
    var room: String? = null
    var store: String? = null
    @SerializedName("phanQuyen")
    var permission: String? = null
    var dienThoai: String? = null
    var diaChi: String? = null
    var idCuaHangMacDinh: String? = null

}