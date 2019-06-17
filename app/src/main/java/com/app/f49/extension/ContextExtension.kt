package com.app.f49.extension

import android.content.Context
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.infoContract.InfoContractActivity
import com.app.f49.activity.quanlythuchi.QuanLyThuChiDetailActivity
import com.app.f49.activity.rutlaicuahang.ThongTinRutLaiCuaHangActivity

fun Context.handleScreenId(screenId: String?, idItem: String?) {
    screenId?.let {
        when (screenId) {

            ScreenIDEnum.RUT_LAI_CHI_TIET.value, ScreenIDEnum.RUT_VON_CHI_TIET.value -> {
                ThongTinRutLaiCuaHangActivity.start(this, idItem, screenId)
            }
            ScreenIDEnum.HOP_DONG_CAM_DO_CHI_TIET.value, ScreenIDEnum.CAM_DO_GIA_DUNG_CHI_TIET.value, ScreenIDEnum.HOP_DONG_TRA_GOP_CHI_TIET.value -> {
                InfoContractActivity.start(this, idItem ?: "", screenId)
            }
            ScreenIDEnum.QUAN_LY_THU_CHI_CHI_TIET.value -> {
                QuanLyThuChiDetailActivity.start(this, idItem?.toIntOrNull())
            }
            else -> {

            }
        }
    }
}