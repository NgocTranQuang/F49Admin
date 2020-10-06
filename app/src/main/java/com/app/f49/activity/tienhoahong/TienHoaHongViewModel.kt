package com.app.f49.activity.tienhoahong

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.DateFilterEnum
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.toStringISO
import com.app.f49.model.nhanvien.HoaHongDTO
import com.app.f49.model.nhanvien.NhanVienDTO
import java.util.*

class TienHoaHongViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var fromDate: MutableLiveData<Date> = MutableLiveData()
    var toDate: MutableLiveData<Date> = MutableLiveData()
    var clickable: MutableLiveData<Boolean> = MutableLiveData()
    var listNhanVien: MutableLiveData<MutableList<NhanVienDTO>> = MutableLiveData()
    var listHoaHong: MutableLiveData<MutableList<HoaHongDTO.HoaHongItem>> = MutableLiveData()

    init {
        fromDate.value = DateFilterEnum.THIS_MONTH.getStartDate()
        toDate.value = DateFilterEnum.THIS_MONTH.getEndDate()
    }


    fun getListHoaHong(idNhanVien: Int?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getTienHoaHong(idNhanVien, fromDate?.value?.toStringISO(), toDate.value?.toStringISO())) {
            listHoaHong?.value = it?.danhSachHopDong ?: mutableListOf()
        }

    }

    fun getListNhanVien() {
        showLoading()
        handleRequestService(mApiService?.getListNhanVien()) {
            it?.let {
                listNhanVien?.value = it
            }
        }

    }
}