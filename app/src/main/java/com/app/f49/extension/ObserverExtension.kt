package com.app.f49.extension

import android.content.Context
import android.util.Log
import com.app.f49.R
import com.app.f49.model.BaseResponse
import com.app.f49.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


//
//
fun <T, B : BaseResponse<T>> Observable<B>.checkRequest(
    context: Context,
    doOnNext: ((T) -> Unit)? = null
): Observable<T?>? {
    @Suppress("DEPRECATION")
    return this.materialize()?.map { noti ->
        noti.isOnError.let {
            noti.error?.let {

                //                if (it.message?.contains(Constants.ERROR_NETWORK) == true) {
//                    throw Throwable(context.getString(R.string.network_not_available), it)
//                } else {
//                    var message = it.message
//                    if ((it as HttpException).code() == 400) {
//                        var messageJson = (noti.error as HttpException).response().errorBody()?.string()
//                        message = Gson().fromJson(messageJson, LoginDTO::class.java).error_description ?: ""
//                        if (message.contains(ACCOUNT_INCORRECT) || message.contains(PASSWORD_INCORRECT)) {
//                            message = message + TRY_AGAIN
//                        }
//                    }
//                    throw Throwable(message, it)
//                }
                Log.e("Retrofit_Error", it.message ?: "")
                if (it.message?.contains(Constants.ERROR_NETWORK) == true) {
                    throw Throwable(context.getString(R.string.network_not_available), it)
                } else {
                    throw Throwable(context.getString(R.string.server_error), it)
                }
//                else {
//                    throw Throwable(it.message, it)
//                }
            }
        }
        noti
    }?.filter {
        !it.isOnError

    }?.dematerialize<BaseResponse<T>>()?.map {
        if (it.success != true) {
            throw Throwable(it.message, Throwable(""))
        }
        it
    }?.filter {
        (it.success == true)
    }?.flatMap {
        Observable.fromArray(it?.data)
    }?.doOnNext {
        it?.let {
            if (doOnNext != null) {
                doOnNext(it)
            }
        }
    }?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
}