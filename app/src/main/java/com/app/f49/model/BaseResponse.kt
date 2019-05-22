package com.app.f49.model

class BaseResponse<T> {
    var success: Boolean? = null
    var message: String? = null
    var msgType: Int? = null
    var data: T? = null
}