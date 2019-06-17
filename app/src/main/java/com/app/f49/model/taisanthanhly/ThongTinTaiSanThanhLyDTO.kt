package com.app.f49.model.taisanthanhly

import com.app.f49.TaiSanThanhLyTypeEnum
import com.google.gson.annotations.SerializedName
import java.util.*

class ThongTinTaiSanThanhLyDTO {
    var soHopDong: String? = null
    @SerializedName("tenVatCamCo")
    var tenTaiSan: String? = null
    var mota: String? = null
    var tenTrangThai: String? = null
        get() = TaiSanThanhLyTypeEnum.get(trangThai)?.getNameEnum()
    var trangThai: Int? = null
    @SerializedName("ngayLuuKho")
    var ngayVao: Date? = null
    @SerializedName("ngayTra")
    var ngayRa: Date? = null
    @SerializedName("ckxkcc")
    var kcc: String? = null
    var coChiaKhoa: Boolean? = null
    var matKhau: String? = null
    @SerializedName("viTriDeDo")
    var noiLuu: String? = null
    @SerializedName("ngayThanhLy")
    var ngayTL: Date? = null
    @SerializedName("giaThanhLyok")
    var soTienTL: String? = null

}