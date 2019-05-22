package vn.com.ttc.ecommerce.extension

import android.widget.ImageView
import com.app.f49.R
import com.bumptech.glide.request.RequestOptions
import vn.com.ttc.ecommerce.service.GlideApp


fun ImageView.setImageURL(url: String?) {
//    url?.let {
//    val circularProgressDrawable = CircularProgressDrawable(context)
//    circularProgressDrawable.strokeWidth = 5f
//    circularProgressDrawable.centerRadius = 30f
//    circularProgressDrawable.start()
    val options = RequestOptions()
        .centerCrop()
        .error(R.mipmap.ic_launcher)
//        .placeholder(circularProgressDrawable)
    GlideApp.with(this).load(url).apply(options).into(this)
//    }
}

fun ImageView.setCircleImageURL(url: String?) {
    val options = RequestOptions()
        .centerCrop()
        .circleCrop()
        .error(R.mipmap.ic_launcher)
    if (url == null) {
        GlideApp.with(this).load(R.drawable.ic_dashboard_black_24dp).apply(options).into(this)
    }
    GlideApp.with(this).load(url).apply(options).into(this)

}