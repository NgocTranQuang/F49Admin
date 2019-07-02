package com.app.f49.activity.camdo

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.status.StatusDTO

class CamdoViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var listStatusDTO: MutableLiveData<MutableList<StatusDTO>> = MutableLiveData()
    var listData: MutableLiveData<MutableList<CamdoDTO>> = MutableLiveData()
    fun getStatus() {
        mApiService?.getStatus()?.checkRequest(mContext)?.subscribe({
            it?.let {
                listStatusDTO.value = it
            }
        }, {
            showDialogError(it)
        }, {

        })
    }

    fun getListDinhGia(idStatus: String) {
        showLoading()
        mApiService?.getListDinhGia(idStatus).checkRequest(mContext)?.subscribe({
            it?.let {
                listData.value = it
            }
        }, {
            if (it.message?.contains("null") == true) {
                listData.value = mutableListOf()
                hideLoading()
            } else {
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }

    fun getListCamDo(idStatus: String) {
        showLoading()
        mApiService?.getListCamDo(idStatus).checkRequest(mContext)?.subscribe({
            it?.let {
                listData.value = it
            }
        }, {
            if (it.message?.contains("null") == true) {
                listData.value = mutableListOf()
                hideLoading()
            } else {
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }

    fun getListDoGiaDung(idStatus: String) {
        showLoading()
        mApiService?.getListDoGiaDung(idStatus).checkRequest(mContext)?.subscribe({
            it?.let {
                listData.value = it
            }
        }, {
            if (it.message?.contains("null") == true) {
                listData.value = mutableListOf()
                hideLoading()
            } else {
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }
}