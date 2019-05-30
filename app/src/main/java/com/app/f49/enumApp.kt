package com.app.f49

import com.app.f49.extension.toUTC
import com.app.f49.utils.DateUtil
import java.util.*

enum class TypeHeader(var value: Int) {
    CAM_DO(0), DINH_GIA(1), DO_GIA_DUNG(2)
}

enum class ScreenIDEnum(var value: String) {
    HOP_DONG_CAM_DO("HopDongCamDo"),
    CAM_DO_GIA_DUNG("CamDoGiaDung"),
    HOP_DONG_TRA_GOP("HopDongTraGop"),
    RUT_LAI("RutLai"),
    BAO_CAO_TONG_HOP("BaoCaoTongHop"),
    TAI_SAN_THANH_LY("TaiSanThanhLy")

}

enum class DateFilterEnum(var value: Int) {
    TODAY(0) {
        override fun getNameDate() = "Ngày hôm nay"
        override fun getStartDate() = Date().toUTC()
        override fun getEndDate() = Date().toUTC()
    },
    YESTERDAY(1) {
        override fun getNameDate() = "Ngày hôm qua"
        override fun getStartDate() = DateUtil.getYesterday().toUTC()
        override fun getEndDate() = DateUtil.getYesterday().toUTC()
    },
    THIS_MONTH(2) {
        override fun getNameDate() = "Tháng này"
        override fun getStartDate() = DateUtil.getStartDateThisMonth().toUTC()
        override fun getEndDate() = DateUtil.getLastDateThisMonth().toUTC()
    },
    LAST_MONTH(3) {
        override fun getNameDate() = "Tháng trước"
        override fun getStartDate() = DateUtil.getStartDateLastMonth().toUTC()
        override fun getEndDate() = DateUtil.getLastDateLastMonth().toUTC()
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
