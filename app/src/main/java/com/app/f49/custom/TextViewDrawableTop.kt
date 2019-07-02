package com.app.f49.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView


class TextViewDrawableTop : TextView {
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    fun initView() {
        this.post {
//            setBackgroundColor(Color.RED)
//            var a = textSize - lineHeight
//            val innerDrawable = compoundDrawables[0]
//
//            val gravityDrawable = GravityCompoundDrawable(innerDrawable, a)
//// NOTE: next 2 lines are important!
//            innerDrawable.setBounds(0, 0, innerDrawable.intrinsicWidth, innerDrawable.intrinsicHeight)
//            gravityDrawable.setBounds(0, 0, innerDrawable.intrinsicWidth, innerDrawable.intrinsicHeight)
//            setCompoundDrawables(gravityDrawable, null, null, null)
        }

    }

}

class GravityCompoundDrawable(// inner Drawable
    private val mDrawable: Drawable, var float: Float) : Drawable() {
    override fun setAlpha(alpha: Int) {

    }

    @SuppressLint("WrongConstant")
    override fun getOpacity(): Int {
        return 1
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getIntrinsicWidth(): Int {
        return mDrawable.intrinsicWidth
    }

    override fun getIntrinsicHeight(): Int {
        return mDrawable.intrinsicHeight
    }

    override fun draw(canvas: Canvas) {
        val halfCanvas = canvas.height / 2
        val halfDrawable = mDrawable.intrinsicHeight / 2

        // align to top
        canvas.save()
        canvas.translate(0F, (-halfCanvas + halfDrawable).toFloat())
        mDrawable.draw(canvas)
        canvas.restore()
    }
}