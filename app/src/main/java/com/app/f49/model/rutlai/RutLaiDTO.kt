package com.app.f49.model.rutlai

import com.app.f49.TrangThaiThongTinRutLaiEnum
import com.google.gson.annotations.SerializedName
import java.util.*

class RutLaiDTO {
    var idItem: String? = null
    var id: Int? = null
    var stt: String? = null
    @SerializedName("tenCuaHang")
    var cuaHang: String? = null
    @SerializedName(value = "ngayThucHien", alternate = ["ngayTao"])
    var ngayThucHien: Date? = null
    @SerializedName("soTien")
    var sotien: Double? = null
    @SerializedName(value = "nguoiThucHien", alternate = ["hoTen"])
    var nguoiThucHien: String? = null
    var trangThai: Int? = null
        get() {
            if (field == null) {
                return TrangThaiThongTinRutLaiEnum.get(trangThaiDisplay)?.value
            }
            return field
        }
    @SerializedName("tenTrangThai")
    var trangThaiDisplay: String? = null
    //        get() = TrangThaiThongTinRutLaiEnum.get(trangThai)?.getNameEnum()
    var ghiChu: String? = null
    var yKien: YKien? = null
    var isShowEditText: Boolean? = null
        get() {
            if (trangThai == TrangThaiThongTinRutLaiEnum.CHO_DUYET.value) {
                return true
            }
            return false
        }

}

class YKien {
    @SerializedName(value = "name",alternate = ["hoTen","HoTen"])
    var name: String? = null
    @SerializedName(value = "noiDung",alternate = ["NoiDung"])
    var content: String? = null
    @SerializedName(value = "thoiGian",alternate = ["ThoiGian"])
    var thoiGian: String? = null
    var nameAndTime: String? = null
        get() = "$name, $thoiGian"
}