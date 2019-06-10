package com.app.f49.model.rutlai

import com.app.f49.TranThaiThongTinRutLaiEnum
import com.google.gson.annotations.SerializedName

class RutLaiDTO {
    var idItem: String? = null
    var id: Int? = null
    var stt: String? = null
    @SerializedName("tenCuaHang")
    var cuaHang: String? = null
    var ngayThucHien: String? = null
    @SerializedName("soTien")
    var sotien: String? = null
    var nguoiThucHien: String? = null
    var trangThai: Int? = null
    @SerializedName("tenTrangThai")
    var trangThaiDisplay: String? = null
    //        get() = TranThaiThongTinRutLaiEnum.get(trangThai)?.getNameEnum()
    var ghiChu: String? = null
    var yKien: YKien? = null
    var isShowEditText: Boolean? = null
        get() {
            if (trangThai == TranThaiThongTinRutLaiEnum.CHO_DUYET.value) {
                return true
            }
            return false
        }

}

class YKien {
    @SerializedName("hoTen")
    var name: String? = null
    @SerializedName("noiDung")
    var content: String? = null
    var thoiGian: String? = null
    var nameAndTime: String? = null
        get() = "$name, $thoiGian"
}