package com.app.f49.binding

import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.f49.R
import com.app.f49.extension.setCircleImageURL
import com.app.f49.extension.toPrice
import com.app.f49.extension.toShow
import com.app.f49.service.GlideApp
import com.bumptech.glide.request.RequestOptions
import java.util.*

@BindingAdapter("android:circleImageUrl")
fun setCircleImageUrl(view: ImageView, url: String?) {
    view.setCircleImageURL(url)
}

@BindingAdapter("android:imageUrl")
fun ImageView.binImageUrl(url: String?) {
    url?.let {
        val options = RequestOptions()
            .error(R.drawable.no_image)
        GlideApp.with(this).load(url).apply(options).into(this)
    }
}

@BindingAdapter("android:imageUrl_no_center")
fun ImageView.binImageUrlWithouCenter(url: String?) {
    url?.let {
        val options = RequestOptions()
            .error(R.drawable.no_image)
        GlideApp.with(this).load(url).apply(options).into(this)
    }
}

@BindingAdapter("android:text_color")
fun TextView.binColor(color: String?) {
    color?.let {
        if (!color.isNullOrBlank()) {
            setTextColor(Color.parseColor(color))
        }else{
            setTextColor(Color.parseColor("#e30000"))
        }
    }
}

@BindingAdapter("android:is_read")
fun TextView.bindIsRead(isRead: Boolean?) {
    if (isRead == true) {
//        typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
        setTextColor(ContextCompat.getColor(context, R.color.color_date_notification))
    } else {
//        typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
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
@BindingAdapter("android:money")
fun TextView.bindMoney(money: Double?) {
    if (money != null) {
        text = money.toPrice()
    } else {
        text = "0"
    }

}
@BindingAdapter("android:money")
fun TextView.bindMoney(money: Long?) {
    if (money != null) {
        text = money.toPrice()
    } else {
        text = "0"
    }

}