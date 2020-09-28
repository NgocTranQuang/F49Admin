package com.app.f49.activity.creatingContract

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import java.util.*

class CreateContractViewModel(app:Application) :BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var dateVay: MutableLiveData<Date> = MutableLiveData()
    init {
        dateVay.value = Date()
    }
}