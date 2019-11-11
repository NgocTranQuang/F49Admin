package com.app.f49.model.quanlythuchi

import com.app.f49.TypeThuChiEnum
import com.app.f49.TypeTrangThaiThuChiEnum
import com.app.f49.model.rutlai.YKien

class QuanLyThuChiDetailDTO {
    var id: Int? = null
    var thu: String? = null
    var tenCuaHang: String? = null
    var chi: String? = null
    var nguoiThucHien: String? = null
    var ngayThucHien: String? = null
    var soTien: String? = null
    var ghiChu: String? = null
    var lyDo: String? = null

    var trangThai: Int? = null
    var trangThaiDisplay: String? = null
        get() {

            return TypeTrangThaiThuChiEnum.get(trangThai)?.getNameEnum()
        }

    var loaiGiaoDich: String? = null
    var yKien: YKien? = null
    var isChi: Boolean? = null
        get() {
            return loaiGiaoDich == TypeThuChiEnum.CHI.value
        }
    var isShowEditText: Boolean? = null
        get() {

            if (trangThai == TypeTrangThaiThuChiEnum.CHO_DUYET.value) {
                return true
            }

            return false
        }

}