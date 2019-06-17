package com.app.f49.activity.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.app.f49.R
import com.app.f49.model.login.LoginDTO
import com.app.f49.utils.Constants
import com.app.f49.utils.PreferenceUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import vn.com.ttc.ecommerce.base.BaseMvvmAndroidViewModel
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.extension.checkRequest

class LoginViewModel(app: Application) : BaseMvvmAndroidViewModel<BaseNavigator>(app) {
    val GRANT_TYPE = "password"
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var isLoginSuccessfully: MutableLiveData<Boolean> = MutableLiveData()
    val TRY_AGAIN = ". Xin vui lòng thử lại."
    val ACCOUNT_INCORRECT = "Tài khoản không tồn tại"
    val PASSWORD_INCORRECT = "Sai mật khẩu"
    var version : MutableLiveData<String> = MutableLiveData()

//    init {
//        email.tenTrangThai = "sanghv@itpsolution.net"
//        password.tenTrangThai = "111111"
//    }

    fun login(email: String, password: String) {

        showLoading()
        mApiService?.login(GRANT_TYPE, email ?: "", password
            ?: "").materialize()?.map { noti ->
            noti.isOnError.let {
                noti.error?.let {
                    if (it.message?.contains(Constants.ERROR_NETWORK) == true) {
                        throw Throwable(mContext.getString(R.string.network_not_available), it)
                    } else {
                        var message = it.message
                        if ((it as HttpException).code() == 400) {
                            var messageJson = (noti.error as HttpException).response().errorBody()?.string()
                            message = Gson().fromJson(messageJson, LoginDTO::class.java).error_description ?: ""
                            if (message.contains(ACCOUNT_INCORRECT) || message.contains(PASSWORD_INCORRECT)) {
                                message = message + TRY_AGAIN
                            }
                        }
                        throw Throwable(message, it)
                    }
                }
            }
            noti
        }?.filter {
            !it.isOnError

        }?.dematerialize<LoginDTO>()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            if (it.error == null) {
                PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_TOKEN, it.token_type + " " + it.access_token)
                PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_EMAIL, email)
                PreferenceUtils.writeString(mContext, PreferenceUtils.KEY_PASSWORD, password)
                PreferenceUtils.writeBoolean(mContext, PreferenceUtils.KEY_IS_LOGOUT, false)
                isLoginSuccessfully.value = true
            } else {
                isLoginSuccessfully.value = false
                showDialogError(it.error_description ?: "")
            }
        }, {
            isLoginSuccessfully.value = false
            showDialogError(it)
        }, {
            hideLoading()
        })
    }

    fun pushTokenFirebase(email: String, token: String, deviceName: String, flg: Boolean) {
        if (email.isNullOrBlank() || token.isNullOrBlank()) {
            showDialogError(mContext.getString(R.string.provide_account))
            return
        }
        mApiService?.pushFirebaseToken(email, token, deviceName, flg).checkRequest(mContext)?.subscribe({
            Log.d("push", "thanh cong")
        }, {
            Log.d("push", "thanh cong")

        }, {
            Log.d("push", "thanh cong")

        })
    }
}