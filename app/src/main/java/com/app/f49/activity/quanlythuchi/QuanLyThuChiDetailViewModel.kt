package com.app.f49.activity.quanlythuchi

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.model.quanlythuchi.QuanLyThuChiDetailDTO

class QuanLyThuChiDetailViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var quanLyThuChiDetailDTO: MutableLiveData<QuanLyThuChiDetailDTO> = MutableLiveData()
    var finished: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailQuanLyThuChi(idItem: Int?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getDetailQuanLyThuChi(idItem)) {
            it?.let {
                it.getOrNull(0)?.let {
                    quanLyThuChiDetailDTO.value = it
                }
            }
        }

    }

    fun duyetChi(yKien: String, isAccept: Boolean) {
        showLoading()
        handleRequestServiceObject(mApiService?.duyetChi(quanLyThuChiDetailDTO.value?.id, yKien, isAccept)) {
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