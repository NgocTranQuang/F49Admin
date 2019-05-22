package com.app.f49.model.home

import java.io.Serializable

class ItemHomeDTO : Serializable {
    var functionValue: String? = null
    var imageUrl: String? = null
    var title: String? = null
    var screenId: String? = null
    var notification: String? = null
    var price: String? = null
    var priceColor: String? = null
    var isShowNotification: Boolean? = null
        get() {
            return !(functionValue.isNullOrBlank() || functionValue == "0" || functionValue == "-1")
        }
}