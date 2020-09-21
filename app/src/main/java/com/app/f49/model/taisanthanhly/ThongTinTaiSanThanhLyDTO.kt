package com.app.f49.model.taisanthanhly

import com.google.gson.annotations.SerializedName

class ThongTinTaiSanThanhLyDTO {
    var soHopDong: String? = null
    @SerializedName("tenVatCamCo")
    var tenTaiSan: String? = null
    var mota: String? = null
    var tenTrangThai: String? = null
//        get() = TaiSanThanhLyTypeEnum.get(trangThai)?.getNameEnum()
    var trangThai: Int? = null
    @SerializedName("ngayLuuKho")
    var ngayVao: String? = null
    @SerializedName("ngayTra")
    var ngayRa: String? = null
    @SerializedName("ckxkcc")
    var kcc: String? = null
    var coChiaKhoa: Boolean? = null
    var matKhau: String? = null
    @SerializedName("viTriDeDo")
    var noiLuu: String? = null
    @SerializedName("ngayThanhLy")
    var ngayTL: String? = null
    @SerializedName("giaThanhLyok")
    var soTienTL: String? = null

}