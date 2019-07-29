package com.app.f49.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class SquareConstrainLayouts(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}