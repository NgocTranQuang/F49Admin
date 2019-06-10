package com.app.f49.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateUtil {
    companion object {
        fun getYesterday(): Date {
            var cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            return cal.time
        }

        fun getStartDateThisMonth(): Date {
            val c = Calendar.getInstance()   // this takes current date
            c.set(Calendar.DAY_OF_MONTH, 1)
            return c.time
        }

        fun getLastDateThisMonth(): Date {
            return Date()
        }

        fun getStartDateLastMonth(): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)

            val max = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
            calendar.set(Calendar.DAY_OF_MONTH, max)

            return calendar.time
        }

        fun getLastDateLastMonth(): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)

            val max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            calendar.set(Calendar.DAY_OF_MONTH, max)

            return calendar.time
        }

        fun getStringUTC(date: Date): String {

            val outputFmt = SimpleDateFormat(Constants.FORMAT_DATE_TIME)
            outputFmt.setTimeZone(TimeZone.getTimeZone("gmt"))
            val gmtTime = outputFmt.format(Date())

//            val calendar = Calendar.getInstance()
//            calendar.timeZone = TimeZone.getTimeZone("UTC")
//            calendar.time = date
//            val time = calendar.time
//            val outputFmt = SimpleDateFormat(Constants.FORMAT_DATE_TIME)
//            val dateAsString = outputFmt.format(time)
            return gmtTime
        }

        fun convertStringToDate(dtStart: String): Date? {
            val format = SimpleDateFormat(Constants.FORMAT_DATE_TIME_ISO)
            try {
                val date = format.parse(dtStart)
                return date
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }
        }

        fun convertDateToString(date: Date?): String {
            val dateFormat = SimpleDateFormat(Constants.FORMAT_DATE_TIME_TO_SHOW)
            try {
                val dateTime = dateFormat.format(date)
                return dateTime
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }

        }
    }
}