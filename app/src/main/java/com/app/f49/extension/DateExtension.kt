package com.app.f49.extension

import com.app.f49.utils.Constants
import com.app.f49.utils.DateUtil
import java.text.SimpleDateFormat
import java.util.*


fun Date.toUTC(): Date {

    val outputFmt = SimpleDateFormat(Constants.FORMAT_DATE_TIME)

    return outputFmt.parse(DateUtil.getStringUTC(this))
}

fun Date.format(format: String): String {
    val sdf = SimpleDateFormat(format)
    val formattedDate = sdf.format(this)
    return formattedDate
}

fun Date.toStringISO(): String {
    val sdf = SimpleDateFormat(Constants.FORMAT_DATE_TIME_ISO)
    val formattedDate = sdf.format(this)
    return formattedDate
}

fun Date.toShow():String {
    return  format(Constants.FORMAT_DATE_TIME_TO_SHOW)
}