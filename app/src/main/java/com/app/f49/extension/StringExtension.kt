package com.app.f49.extension

import android.text.TextUtils
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


fun String.toDateDisplay(): String {
//    var date = DateUtil.convertStringToDate(this)
//     DateUtil.convertDateToString(date)
    return this
}

fun String.toPrice(): String {
    return formatVnCurrence(this)
}

fun formatVnCurrence(price: String): String {
    var price = price

    val format = DecimalFormat("#,##0.00")// #,##0.00 ¤ (¤:// Currency symbol)
    format.setCurrency(Currency.getInstance(Locale.US))//Or default locale

    price = if (!TextUtils.isEmpty(price)) price else "0"
    price = price.trim { it <= ' ' }
    price = format.format(java.lang.Double.parseDouble(price))
//    price = price.replace(",".toRegex(), "\\.")

    if (price.endsWith(".00")) {
        val centsIndex = price.lastIndexOf(".00")
        if (centsIndex != -1) {
            price = price.substring(0, centsIndex)
        }
    }
    price = String.format("%s đ", price)
    return price
}

fun formatVND(price: Double): String {
    var moneyUSD = NumberFormat.getCurrencyInstance(Locale.US).format(price)
    moneyUSD = moneyUSD.replace("$", "")
    if (moneyUSD.endsWith(".00")) {
        val centsIndex = moneyUSD.lastIndexOf(".00")
        if (centsIndex != -1) {
            moneyUSD = moneyUSD.substring(0, centsIndex)
        }
    }
    moneyUSD = String.format("%s đ", moneyUSD)
    return moneyUSD
}