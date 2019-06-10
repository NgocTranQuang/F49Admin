package com.app.f49.model.home

import java.io.Serializable

class ItemHomeDTO : Serializable {
    //    var functionValue: String? = null
//    var imageUrl: String? = null
//    var title: String? = null
    var screenId: String? = null
    //    var notification: String? = null
    var price: String? = null
//    var priceColor: String? = null
//    var isShowNotification: Boolean? = null
//        get() {
//            return !(functionValue.isNullOrBlank() || functionValue == "0" || functionValue == "-1" || screenId == "ThuTrongThang" || screenId == "ChiTrongThang")
//        }

    // Dashboard DTO

    var id: String? = null
    var tieuDe: String? = null
    var giaTri: String? = null
    var hinhAnh: String? = null
    //    var screenId : String?=null
    var phanQuyen: String? = null
    var mauSac: String? = null
    var sapXep: Int? = null
    var isShowPrice: Boolean? = null
        get() {
            if (screenId == "ThuTrongThang" || screenId == "ChiTrongThang") {
                return true
            }
            if (giaTri.isNullOrBlank()) {
                return false
            }
            if ((giaTri?.toIntOrNull() ?: 0) > 0) {
                return true
            }
            return false
        }
    var isShowValue: Boolean? = null
        get() {
            return !(giaTri.isNullOrBlank() || ((giaTri?.toIntOrNull()
                ?: 0) < 1) || screenId == "ThuTrongThang" || screenId == "ChiTrongThang")
        }
}