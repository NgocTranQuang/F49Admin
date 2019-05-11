package com.app.f49.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and


object GeneralUtils {

    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun initAlertDialog(context: Context, v: View, isCancelable: Boolean): AlertDialog {
        val alert = AlertDialog.Builder(context)
        alert.setView(v)
        alert.setCancelable(isCancelable)
        val alertDialog = alert.create()
        val window = alertDialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return alertDialog
    }

    /**
     * Check if any network is active
     *
     * @param context Context
     * @return boolean
     */


    fun showCustomToast(context: Context, msg: String, bgColor: Int) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        val view = toast.view

        //To change the Background of Toast
        view.setBackgroundColor(bgColor)
        val text = view.findViewById<TextView>(android.R.id.message)
        text.setTextColor(Color.WHITE)

        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.show()
    }

    fun hideKeyboard(context: Context, v: View?) {
        if (v == null) {
            return
        }
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }



    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }

    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun makeSHA1Hash(input: String?): String? {
        if (TextUtils.isEmpty(input)) {
            return input
        }
        val md = MessageDigest.getInstance("SHA1")
        md.reset()
        val buffer = input!!.toByteArray(charset("UTF-8"))
        md.update(buffer)
        val digest = md.digest()

        var hexStr = ""
        for (i in digest.indices) {
            hexStr += Integer.toString((digest[i] and 0xff.toByte()) + 0x100, 16).substring(1)
        }
        return hexStr
    }

    fun makeMD5Hash(input: String?): String? {
        if (TextUtils.isEmpty(input)) {
            return input
        }
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest.getInstance("MD5")
            digest.update(input!!.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices) {
                var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return Constants.EMPTY_STRING
    }

    fun validEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    /**
     * Convert a translucent themed Activity
     * [android.R.attr.windowIsTranslucent] to a fullscreen opaque
     * Activity.
     *
     *
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the [android.view.Surface] of
     * the Activity behind to be released.
     *
     *
     * This call has no effect on non-translucent activities or on activities
     * with the [android.R.attr.windowIsFloating] attribute.
     */
    fun convertActivityFromTranslucent(activity: Activity) {
        try {
            val method = Activity::class.java.getDeclaredMethod("convertFromTranslucent")
            method.isAccessible = true
            method.invoke(activity)
        } catch (t: Throwable) {
        }

    }

    /**
     * Convert a translucent themed Activity
     * [android.R.attr.windowIsTranslucent] back from opaque to
     * translucent following a call to
     * [.convertActivityFromTranslucent] .
     *
     *
     * Calling this allows the Activity behind this one to be seen again. Once
     * all such Activities have been redrawn
     *
     *
     * This call has no effect on non-translucent activities or on activities
     * with the [android.R.attr.windowIsFloating] attribute.
     */
    fun convertActivityToTranslucent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity)
        } else {
            convertActivityToTranslucentBeforeL(activity)
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms before Android 5.0
     */
    fun convertActivityToTranslucentBeforeL(activity: Activity) {
        try {
            val classes = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            for (clazz in classes) {
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                }
            }
            val method = Activity::class.java.getDeclaredMethod(
                "convertToTranslucent",
                translucentConversionListenerClazz!!
            )
            method.isAccessible = true
            method.invoke(activity, arrayOf<Any>())
        } catch (t: Throwable) {
        }

    }

    /**
     * Calling the convertToTranslucent method on platforms after Android 5.0
     */
    private fun convertActivityToTranslucentAfterL(activity: Activity) {
        try {
            val getActivityOptions = Activity::class.java.getDeclaredMethod("getActivityOptions")
            getActivityOptions.isAccessible = true
            val options = getActivityOptions.invoke(activity)

            val classes = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            for (clazz in classes) {
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                }
            }
            val convertToTranslucent = Activity::class.java.getDeclaredMethod(
                "convertToTranslucent",
                translucentConversionListenerClazz, ActivityOptions::class.java
            )
            convertToTranslucent.isAccessible = true
            convertToTranslucent.invoke(activity, null, options)
        } catch (t: Throwable) {
        }

    }

}