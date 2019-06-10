package com.app.f49

import com.app.f49.utils.DateUtil
import java.util.*

enum class TypeHeader(var value: Int) {
    CAM_DO(0), DINH_GIA(1), DO_GIA_DUNG(2)
}

enum class ScreenIDEnum(var value: String) {
    HOP_DONG_CAM_DO("HopDongCamDo"),
    HOP_DONG_CAM_DO_CHI_TIET("HopDongCamDo_ChiTiet"),
    CAM_DO_GIA_DUNG("CamDoGiaDung"),
    CAM_DO_GIA_DUNG_CHI_TIET("CamDoGiaDung_ChiTiet"),
    HOP_DONG_TRA_GOP("HopDongTraGop"),
    HOP_DONG_TRA_GOP_CHI_TIET("HopDongTraGop_ChiTiet"),
    RUT_LAI("RutLai"),
    RUT_LAI_CHI_TIET("RutLai_ChiTiet"),
    BAO_CAO_TONG_HOP("BaoCaoTongHop"),
    QUAN_LY_THU_CHI("ThuChi"),
    QUAN_LY_THU_CHI_CHI_TIET("ThuChi_ChiTiet"),
    TAI_SAN_THANH_LY("TaiSanThanhLy"),
    RUT_VON("RutVon"),
    RUT_VON_CHI_TIET("RutVon_ChiTiet"),


}

enum class DateFilterEnum(var value: Int) {
    TODAY(0) {
        override fun getNameDate() = "Ngày hôm nay"
        override fun getStartDate() = Date()
        override fun getEndDate() = Date()
    },
    YESTERDAY(1) {
        override fun getNameDate() = "Ngày hôm qua"
        override fun getStartDate() = DateUtil.getYesterday()
        override fun getEndDate() = DateUtil.getYesterday()
    },
    THIS_MONTH(2) {
        override fun getNameDate() = "Tháng này"
        override fun getStartDate() = DateUtil.getStartDateThisMonth()
        override fun getEndDate() = DateUtil.getLastDateThisMonth()
    },
    LAST_MONTH(3) {
        override fun getNameDate() = "Tháng trước"
        override fun getStartDate() = DateUtil.getStartDateLastMonth()
        override fun getEndDate() = DateUtil.getLastDateLastMonth()
    },
    ALL_TIME(4) {
        override fun getNameDate() = "Toàn thời gian"
        override fun getStartDate() = null
        override fun getEndDate() = null
    },
    FROM_TO(5) {
        override fun getNameDate() = "Từ ngày đến ngày"
        override fun getStartDate() = Date()
        override fun getEndDate() = Date()
    };

    companion object {
        fun getListDateFilter(): MutableList<DateFilterEnum> {
            return mutableListOf(TODAY, YESTERDAY, THIS_MONTH, LAST_MONTH, ALL_TIME, FROM_TO)
        }

        private val map = DateFilterEnum.values().associateBy(DateFilterEnum::value)
        fun get(type: Int) = map[type]
    }

    abstract fun getNameDate(): String
    abstract fun getStartDate(): Date?
    abstract fun getEndDate(): Date?

}

enum class TranThaiThongTinRutLaiEnum(var value: Int) {
    DA_DUYET(3) {
        override fun getNameEnum() = "Đã duyệt"
    },
    CHO_DUYET(1) {
        override fun getNameEnum() = "Chờ duyệt"
    },
    HUY(2) {
        override fun getNameEnum() = "Từ chối"
    };

    companion object {
        private val map = TranThaiThongTinRutLaiEnum.values().associateBy(TranThaiThongTinRutLaiEnum::value)
        fun get(type: Int?) = map[type]

    }

    abstract fun getNameEnum(): String

}

//enum class TypeHopDongDashBoardEnum(var value: Int) {
//    HOP_DONG_CAM_DO(0),
//    CAM_DO_GIA_DUNG(1),
//    HOP_DONG_TRA_GOP(2)
//}