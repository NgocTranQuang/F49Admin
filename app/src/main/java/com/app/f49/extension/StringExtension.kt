package com.app.f49.extension

import com.google.gson.Gson
import java.text.NumberFormat
import java.util.*

fun Double.toPrice(): String {
    return formatVND(this)
}

fun Long.toPrice(): String {
    return formatVND(this)
}

fun formatVND(price: Double): String {
    return formatVND(price.toLong())
}

fun formatVND(price: Long): String {
    try {
        var moneyUSD = NumberFormat.getCurrencyInstance(Locale.US).format(price)
        moneyUSD = moneyUSD.replace("$", "")
        if (moneyUSD.endsWith(".00")) {
            val centsIndex = moneyUSD.lastIndexOf(".00")
            if (centsIndex != -1) {
                moneyUSD = moneyUSD.substring(0, centsIndex)
            }
        }
        moneyUSD = moneyUSD.replace(",", ".")
//        moneyUSD = String.format("%s đ", moneyUSD)
        return moneyUSD
    } catch (e: Exception) {
        return "0"
    }

}
