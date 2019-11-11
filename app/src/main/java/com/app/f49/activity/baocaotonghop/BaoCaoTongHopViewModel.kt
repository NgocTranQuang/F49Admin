package com.app.f49.activity.baocaotonghop

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.DateFilterEnum
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.toStringISO
import com.app.f49.model.baocaotonghop.BaoCaoTongHopDTO
import java.util.*

class BaoCaoTongHopViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {

    var fromDate: MutableLiveData<Date> = MutableLiveData()
    var toDate: MutableLiveData<Date> = MutableLiveData()
    var clickable: MutableLiveData<Boolean> = MutableLiveData()
    var baocaotonghopDTO: MutableLiveData<BaoCaoTongHopDTO> = MutableLiveData()

    init {
        fromDate.value = DateFilterEnum.THIS_MONTH.getStartDate()
        toDate.value = DateFilterEnum.THIS_MONTH.getEndDate()
    }
    fun getBaoCaoTongHop(itemId: Int?) {
        showLoading()
        handleRequestServiceObject(mApiService?.getBaoCaoTongHop(itemId,fromDate?.value?.toStringISO(), toDate.value?.toStringISO())) {
            it?.let { baocaotonghopDTO.value = it }
        }
    }
}