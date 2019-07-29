package com.app.f49.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.app.f49.R
import com.app.f49.activity.base.BaseActivity
import com.app.f49.extension.setImageURL
import com.google.android.flexbox.FlexboxLayout
import extension.showDialogAsk


class CustomFlexboxLayoutImage : FlexboxLayout {
    var listData: MutableList<String> = mutableListOf()
    var listDataBase64: MutableList<String> = mutableListOf()
    var maxItem: Int = -1
    var isAcceptDeleting = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setIsAcceptDeleting(isAccept: Boolean) {
        isAcceptDeleting = isAccept
    }

    fun setValue(list: MutableList<String>, listBase64: MutableList<String>?) {
        if (isAvailableToAddImage(list)) {
            this.post {
                list?.let {

                    var marginrRight = (resources.getDimension(R.dimen.dp_30)).toInt()

                    var widthItem = (width - 3 * marginrRight) / 4
                    var buttonLayoutParams =
                        LinearLayout.LayoutParams(widthItem, widthItem)

                    buttonLayoutParams?.setMargins(0, 0, marginrRight, 0)
                    fun addImageView(url: String, posotion: Int) {
                        val imageView = ImageView(context)
                        imageView.layoutParams = buttonLayoutParams
                        imageView.setImageURL(url)
                        addView(imageView)
                        setOnLongClickListener(imageView, posotion)
                    }
                    list.forEachIndexed { index, s ->
                        var index = index + listData.size
                        if (index < 11) {
                            addImageView(s, index)
                        }
                    }

                    listData.addAll(list)
                    listBase64?.let {
                        listDataBase64.addAll(it)
                    }
                }
            }
        }
    }

    fun setValue(list: MutableList<String>?) {
        list?.let {
            setValue(list, null)
        }
    }

    fun resetValue() {
        var newList = mutableListOf<String>()
        newList.addAll(listData)
        var newListbase64 = mutableListOf<String>()
        newListbase64.addAll(listDataBase64)
        listData.clear()
        listDataBase64.clear()
        setValue(newList)
    }

    fun setOnLongClickListener(view: View, posotion: Int) {
        view.setOnLongClickListener {
            if (isAcceptDeleting) {
                showDialogAsk(context.getString(R.string.are_you_sure_delete_this_image)) {
                    listData.removeAt(posotion)
                    listDataBase64.removeAt(posotion)
                    removeAllViews()
                    resetValue()
                }
            }
            false
        }
    }

    fun isAvailableToAddImage(listImageAdd: MutableList<String>?): Boolean {
        if (listData.size + (listImageAdd?.size ?: 0) > maxItem && maxItem != -1) {
            var mErrorDialog = ErrorDialog(context as BaseActivity)
            mErrorDialog.setErrorMsg("Chỉ dc upload tối đa 10 ảnh.")
            mErrorDialog.show()
            return false
        }
        return true
    }
}