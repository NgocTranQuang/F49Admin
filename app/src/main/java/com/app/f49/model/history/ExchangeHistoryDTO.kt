package com.app.f49.model.history

import com.app.f49.extension.toShow
import java.util.*

class ExchangeHistoryDTO {
    var id: Int? = null
    var stt: String? = null
    var nhanVien: String? = null
    var idHopDong: Int? = null
    var ngayGiaoDich: Date? = null
    var ngayGiaoDichDisplay: String? = null
        get() {
            return "Ngày giao dịch: " + ngayGiaoDich?.toShow()
        }
    var choVay: Long? = null
    var noGoc: Long? = null
}