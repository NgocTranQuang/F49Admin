package com.app.f49.model.infocontract

import java.util.*

class InfoContractDTO {
    var id: String? = null
    var hinhAnh: MutableList<HinhAnh>? = mutableListOf()
    var numberContract: String? = null
    var fullName: String? = null
    var phoneNumber: String? = null
    var duNo: String? = null
    var expiredDate: Date? = null
    var plusMin: String? = null
    var interest: String? = null
    var fee: String? = null
    var total: String? = null
    var nhacNo: String? = null
    var appointmentDate: Date? = null
    var content: String? = null
    var doDeLai: String? = null
    var countLichSuGiaodich: Int? = null
    var countLichSuVay: Int? = null
    var idKhachHang: String? = null
}

class HinhAnh {
    var url: String? = ""
}