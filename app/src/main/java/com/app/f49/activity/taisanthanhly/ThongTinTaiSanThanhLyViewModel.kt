package com.app.f49.activity.taisanthanhly

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.taisanthanhly.ThongTinTaiSanThanhLyDTO
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator

class ThongTinTaiSanThanhLyViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var thongTinTaiSanThanhLyDTO : MutableLiveData<ThongTinTaiSanThanhLyDTO> = MutableLiveData()
    fun getThongTinTaiSanChiTiet(idItem: Int?) {
        handleRequestServiceObject(mApiService?.getTaiSanDetail(idItem)) {
            thongTinTaiSanThanhLyDTO?.value = it
        }
    }
}