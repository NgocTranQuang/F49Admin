package com.app.f49.activity.uploadimage

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import com.app.f49.extension.checkRequest
import com.app.f49.model.uploadImage.UploadImageDTO

class UploadImageViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    var currentIndex: MutableLiveData<Int> = MutableLiveData()
    var listImageUpload: MutableList<UploadImageDTO> = mutableListOf()
    var finishedUploading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        currentIndex.value = -1
    }

    fun uploadImage() {
        if (listImageUpload.size < 1) {
            showDialogError(mContext.getString(R.string.chon_anh))
            return
        }
        if (listImageUpload.size > (currentIndex.value ?: 0) + 1) {
            currentIndex.value = currentIndex.value!! + 1
            uploadOneImage(listImageUpload.get(currentIndex.value!!))
        } else {
            finishedUploading.value = true
        }
    }

    fun uploadOneImage(image: UploadImageDTO) {
        mApiService?.uploadImage(image)?.checkRequest(mContext)?.subscribe({
            uploadImage()
        }, {
            currentIndex.value = -1
            finishedUploading.value = false
        }, {

        })

    }
}