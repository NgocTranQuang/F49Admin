package com.app.f49.model.taisanthanhly

import java.util.*

class TaiSanDTO {
    var id: Int? = null
    var stt: String? = null
    var title : String?=null
    get() {
        return "$soHopDong - $tenVatCamCo"
    }
    var soHopDong: String? = null
    var tenVatCamCo: String? = null
    var ngayThanhLy: Date? = null
    var dienThoai: String? = null
    var hoTen: String? = null

}