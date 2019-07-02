package com.app.f49.base

import android.app.Application
import android.text.TextUtils
import com.app.f49.F49Application
import com.app.f49.R
import com.app.f49.extension.checkRequest
import com.app.f49.model.BaseResponse
import com.app.f49.service.ApiService
import com.app.f49.service.ServiceRepository
import com.app.f49.utils.Constants
import com.app.f49.utils.NetworkAvailability
import com.app.f49.utils.PreferenceUtils
import io.reactivex.Observable
import net.orionlab.androidmvvm.MvvmAndroidViewModel
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseMvvmAndroidViewModel<N : BaseNavigator>(app: Application) : MvvmAndroidViewModel(app) {
    protected val mContext = (app as F49Application).getConfigLocale(app.baseContext)
    protected val mApiService = ServiceRepository.createService(mContext, ApiService::class.java)
    protected var mRequestCount = 0
    protected var pageSize = PreferenceUtils.getInt(mContext, PreferenceUtils.KEY_PAGE_SIZE, Constants.PAGE_SIZE_DEFAULT)


    var mNavigator: N? = null

    fun setNavigator(navigator: N) {
        mNavigator = navigator
    }

    fun getNavigator(): N {
        return mNavigator!!
    }

    protected fun getString(strId: Int): String {
        return mContext.getString(strId)
    }

    protected fun handleServiceException(t: Throwable) {
        handleServiceException(t)
    }

    protected fun showDialogError(t: Throwable) {
        hideLoading()
        mNavigator?.showErrorDialogWithoutRetry(t.message)
    }

    protected fun showDialogError(message: String) {
        hideLoading()
        mNavigator?.showErrorDialog(message)
    }

    protected fun showDialogAction(msg: String, action: () -> Unit) {
        mNavigator?.showActionDialog(msg, action)
    }

    protected fun showToastMs(ms: String) {
        hideLoading()
        mNavigator?.showToastNoticeMsg(ms)
    }

    protected fun handleServiceExceptionWithoutDismissDialog(t: Throwable) {
        t.printStackTrace()
        if (t is ConnectException || t is SocketTimeoutException ||
            t is UnknownHostException || t is IOException
        ) {
            mNavigator!!.showErrorDialog(getString(R.string.cannot_connect_server))
        } else {
            mNavigator!!.showErrorDialog(getString(R.string.server_error))
        }
    }


    protected fun showLoading() {
        mNavigator?.showLoading(true)
    }

    protected fun hideLoading() {
        mNavigator?.hideLoading()
    }

    protected fun showLoadingViewMultiRequest() {
        mNavigator?.showLoading(false)
        mRequestCount++
    }

    protected fun hideLoadingViewMultiRequest() {
        if (mRequestCount > 0) {
            mRequestCount--
        }
        if (mRequestCount == 0) {
            mNavigator?.hideLoading()
        }
    }


//    protected fun saveInDataBaseLocal(
//        token: String?,
//        fullName: String?,
//        email: String?,
//        phoneNumber: String?,
//        avatar: String?,
//        gender: Int?, dateOfBirth: Date? = null
//    ) {
//        /*
//        save in db local
//        */
//        token?.let {
//            PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_TOKEN, token)
//        }
//        PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_FULL_NAME, fullName)
//        PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_EMAIL, email)
//        PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_PHONE_NUMBER, phoneNumber)
//        PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_AVATAR, avatar)
//        /*
//        save in base
//         */
////        Base?.username?.fullName?.tenTrangThai = fullName
////        Base?.username?.email?.tenTrangThai = email
////        Base?.username?.phoneNumber = phoneNumber
////        Base?.username?.avatarURL?.tenTrangThai = avatar
////        Base?.username?.gender?.tenTrangThai = gender
////        Base?.username?.dateOfBirth?.tenTrangThai = dateOfBirth
//    }

    protected fun isNetworkAvailable(): Boolean {
        return NetworkAvailability.isNetworkAvailable(mContext)
    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(PreferenceUtils.getString(mContext, PreferenceUtils.KEY_TOKEN, null))
    }

    fun logOut() {
        PreferenceUtils.remove(mContext, PreferenceUtils.KEY_TOKEN)
        PreferenceUtils.remove(mContext, PreferenceUtils.KEY_FULL_NAME)
        PreferenceUtils.remove(mContext, PreferenceUtils.KEY_EMAIL)
        PreferenceUtils.remove(mContext, PreferenceUtils.KEY_PHONE_NUMBER)
        PreferenceUtils.remove(mContext, PreferenceUtils.KEY_AVATAR)
        PreferenceUtils.remove(mContext, PreferenceUtils.KEY_GENDER)

//        Base?.username?.fullName?.tenTrangThai = null
//        Base?.username?.email?.tenTrangThai = null
//        Base?.username?.phoneNumber = null
//        Base?.username?.avatarURL?.tenTrangThai = null
//        Base?.username?.gender?.tenTrangThai = null
    }

    fun <T> handleRequestService(obsever: Observable<BaseResponse<MutableList<T>>>, onResult: (MutableList<T>?) -> Unit) {
        obsever.checkRequest(mContext)?.subscribe({
            onResult.invoke(it)
        }, {
            if (it.message?.contains(Constants.KEY_ITEM_NULL) == true) {
                onResult.invoke(mutableListOf())
                hideLoading()
            } else {
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }

    fun <T> handleRequestServiceObject(obsever: Observable<BaseResponse<T>>, onResult: (T?) -> Unit) {
        obsever.checkRequest(mContext)?.subscribe({
            onResult.invoke(it)
        }, {
            if (it.message?.contains(Constants.KEY_ITEM_NULL) == true) {
                onResult.invoke(null)
                hideLoading()
            } else {
                showDialogError(it)
            }
        }, {
            hideLoading()
        })
    }
}