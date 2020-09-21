package com.app.f49.activity.thulai

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.toStringISO
import com.app.f49.model.thulai.ThuLaiDTO
import com.app.f49.model.thulai.ValueResponse
import java.util.*

class ThuLaiViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listLoaiGiaoDich: MutableLiveData<MutableList<ValueResponse>> = MutableLiveData()
    var thulaiDTO: MutableLiveData<ThuLaiDTO> = MutableLiveData()
    var dateHieuLuc: MutableLiveData<Date> = MutableLiveData()
    var phaiThu: MutableLiveData<Double> = MutableLiveData()

    init {
        dateHieuLuc.value = Date()
    }

    fun getLoaiGiaoDich() {
        handleRequestServiceObject(mApiService.getLoaiGiaoDich()) {
            listLoaiGiaoDich.value = it
        }
    }

    fun getThuLaiChiTiet(idHopDong: Int?) {
        handleRequestServiceObject(mApiService.getChiTietHopDongThuLai(idHopDong,dateHieuLuc?.value?.toStringISO())) {
            thulaiDTO.value = it ?: ThuLaiDTO()
        }
    }

    fun putThucHienThuLai(idHopDong: Int?, idLoaiGiaoDich: Int?, idCuaHangFormApp: Int?, tienThuThucTe: Double?, nameAction: String, finished: () -> Unit) {
        showLoading()
        handleRequestServiceObject(mApiService.putThucHienThuLai(idHopDong, idLoaiGiaoDich, idCuaHangFormApp, tienThuThucTe, dateHieuLuc?.value?.toStringISO())) {
            hideLoading()
            showDialogAction("Thực hiện ${nameAction} Thành công.") {
                finished?.invoke()
            }
//            thulaiDTO.value = it ?: ThuLaiDTO()
        }
    }
}