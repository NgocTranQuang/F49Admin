package com.app.f49.activity.rutlaicuahang

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.rutlai.RutLaiDTO

class ThongTinRutLaiViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {

    var thongTinRutLai: MutableLiveData<RutLaiDTO> = MutableLiveData()
    var finished: MutableLiveData<Boolean> = MutableLiveData()
    fun getThongTinRutLaiChiTiet(id: String?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getDetaiRutLailById(id?.toInt())) {
            it?.let {
                thongTinRutLai.value = it
            }
        }
    }

    fun getThongTinRutVonChiTiet(id: String?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getDetaiRutVonlById(id?.toInt())) {
            it?.let {
                thongTinRutLai.value = it
            }
        }
    }

    fun duyetRutLai(yKien: String, isAccept: Boolean) {
        showLoading()
        handleRequestServiceObject(mApiService?.duyetRutLai(thongTinRutLai.value?.id, yKien, isAccept)) {
            var msg = ""
            if (isAccept) {
                msg = mContext.getString(R.string.duyet_successfully)
            } else {
                msg = mContext.getString(R.string.tuchoi_successfully)
            }
            showDialogAction(msg) {
                finished.value = true
            }
        }
    }

    fun duyetRutVon(yKien: String, isAccept: Boolean) {
        showLoading()
        handleRequestServiceObject(mApiService?.duyetRutVon(thongTinRutLai.value?.id, yKien, isAccept)) {
            var msg = ""
            if (isAccept) {
                msg = mContext.getString(R.string.duyet_successfully)
            } else {
                msg = mContext.getString(R.string.tuchoi_successfully)
            }
            showDialogAction(msg) {
                finished.value = true
            }
        }
    }
}