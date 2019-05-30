package com.app.f49.extension

import com.app.f49.utils.Constants
import com.app.f49.utils.DateUtil
import java.text.SimpleDateFormat
import java.util.*


fun Date.toUTC(): Date {

    val outputFmt = SimpleDateFormat(Constants.FORMAT_DATE_TIME)

    return outputFmt.parse(DateUtil.getStringUTC(this))
}