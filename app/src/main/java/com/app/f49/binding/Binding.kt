package com.app.f49.binding

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.f49.R
import com.app.f49.extension.toShow
import com.bumptech.glide.request.RequestOptions
import vn.com.ttc.ecommerce.extension.setCircleImageURL
import vn.com.ttc.ecommerce.service.GlideApp
import java.util.*

@BindingAdapter("android:circleImageUrl")
fun setCircleImageUrl(view: ImageView, url: String?) {
    view.setCircleImageURL(url)
}

@BindingAdapter("android:imageUrl")
fun ImageView.binImageUrl(url: String?) {
    url?.let {
        val options = RequestOptions()
            .centerCrop()
            .error(R.mipmap.ic_launcher)
        GlideApp.with(this).load(url).apply(options).into(this)
    }
}

@BindingAdapter("android:imageUrl_no_center")
fun ImageView.binImageUrlWithouCenter(url: String?) {
    url?.let {
        val options = RequestOptions()
            .error(R.mipmap.ic_launcher)
        GlideApp.with(this).load(url).apply(options).into(this)
    }
}

@BindingAdapter("android:text_color")
fun TextView.binColor(color: String?) {
    color?.let {
        if (!color.isNullOrBlank())
            setTextColor(Color.parseColor(color))
    }
}

@BindingAdapter("android:is_read")
fun TextView.bindIsRead(isRead: Boolean?) {
    if (isRead == true) {
        typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
        setTextColor(ContextCompat.getColor(context, R.color.color_date_notification))
    } else {
        typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
        setTextColor(ContextCompat.getColor(context, R.color.color_text))
    }

}

@BindingAdapter("android:view_bg_color")
fun ViewGroup.binBgColor(color: String?) {
    if (color != null && color != "") {
        setBackgroundColor(Color.parseColor(color))
    } else {
        setBackgroundColor(Color.WHITE)
    }
}

@BindingAdapter("android:date")
fun TextView.bindDate(date: Date?) {
    if (date != null) {
        text = date.toShow()
    } else {
        text = ""
    }

}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShifting(false)
            // set once again checked tenTrangThai, so view will be updated
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("BottomNavigationView", "Unable to get shift mode field", e)
    } catch (e: IllegalStateException) {
        Log.e("BottomNavigationView", "Unable to change tenTrangThai of shift mode", e)
    }
}