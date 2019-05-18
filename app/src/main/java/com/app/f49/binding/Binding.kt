package com.app.f49.binding

import android.databinding.BindingAdapter
import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vn.com.ttc.ecommerce.extension.setCircleImageURL
import vn.com.ttc.ecommerce.service.GlideApp
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:circleImageUrl")
fun setCircleImageUrl(view: ImageView, url: String?) {
    view.setCircleImageURL(url)
}

@BindingAdapter("android:imageUrl")
fun ImageView.binImageUrl(url: String?) {
    url?.let {
        GlideApp.with(this).load(url).into(this)
    }
}

@BindingAdapter("android:text_color")
fun TextView.binColor(color: String?) {
    color?.let {
        setTextColor(Color.parseColor(color))
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
    date?.let {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val strDate = dateFormat.format(date)
        text = strDate
    }
}