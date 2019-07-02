package com.app.f49.custom

import android.content.Context
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.SnapHelper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.app.f49.R
import com.app.f49.custom.SnapOnScrollListener.Companion.NOTIFY_ON_SCROLL
import com.app.f49.utils.Constants
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.recyclerview_with_indicator_custom.view.*

class CustomRvWithIndicator(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    var timeSlide: Float = 0f
    private var infiniteAdapter: InfiniteScrollAdapter<*>? = null
    var adapterBanner: AdapterBanner? = null
    var isAutoSlide = true
    var mHandler: Handler? = null
    var runnable: Runnable? = null
    var countItem = 0
    var currentPosition = 0;
    var scale = .9f
    var eventClickImageBanner: ((View, Int) -> Unit)? = null
    var clickFavouriteListener: ((ImageView) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)


    init {
        LayoutInflater.from(context).inflate(R.layout.recyclerview_with_indicator_custom, this, true)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.custom_rv_with_indicator)
            var isSquareCardView = a.getBoolean(R.styleable.custom_rv_with_indicator_square_cardview, false)
//            var mediaStyle = a.getInt(R.styleable.custom_rv_with_indicator_stype, ContentProductTypeEnum.IMAGE.getTypeNumber())

            adapterBanner = AdapterBanner(mutableListOf(), false, 0) { view, po ->
                eventClickImageBanner?.invoke(view, po)
            }
            adapterBanner?.let {
                infiniteAdapter = InfiniteScrollAdapter.wrap(it)
            }
            dScrollview.adapter = infiniteAdapter
            dScrollview.setOrientation(DSVOrientation.HORIZONTAL)
            dScrollview.setOverScrollEnabled(true)
            dScrollview.setItemTransitionTimeMillis(Constants.VIEWPAGER_SCROLL_DURATION.toInt() / 5)
            dScrollview.setSlideOnFling(false)
            dScrollview.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                    mHandler?.removeCallbacks(runnable)
                } else if (event.action == MotionEvent.ACTION_UP) {
                    if (isAutoSlide)
                        handleAutoSlide()
                }
                false
            }

            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(dScrollview)
            dScrollview.addOnScrollListener(SnapOnScrollListener(snapHelper, NOTIFY_ON_SCROLL) { position ->
                setIndicator(position)
            })
            isAutoSlide = a.getBoolean(R.styleable.custom_rv_with_indicator_isAuto_slide, true)
            var showFavourite = a.getBoolean(R.styleable.custom_rv_with_indicator_show_image_favourite, true)
            var selectedColor: Int = a.getColor(
                R.styleable.custom_rv_with_indicator_color_selected,
                ContextCompat.getColor(context, R.color.colorAccent)
            )
            pageIndicatorView.selectedColor = selectedColor
            var unSelectedColor: Int = a.getColor(
                R.styleable.custom_rv_with_indicator_color_unSelected,
                ContextCompat.getColor(context, R.color.color_indicator_unSelected)
            )
            pageIndicatorView.unselectedColor = unSelectedColor
            timeSlide = a.getFloat(
                R.styleable.custom_rv_with_indicator_time_silde,
                Constants.PERIOD_AUTO_SCROLL_SLIDER.toFloat()
            )
            scale = a.getFloat(R.styleable.custom_rv_with_indicator_scale, scale)
            dScrollview.setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(scale)
                    .build()
            )
            a.recycle()
        }
    }


    fun setEventClickBanner(eventClickImageBanner: ((View, Int) -> Unit)?) {
        this.eventClickImageBanner = eventClickImageBanner
    }

    fun setEventClickFavouriteListtener(listener: (ImageView) -> Unit) {
        clickFavouriteListener = listener
    }

    fun setIndicator(po: Int) {
        var realPo = getRealPosition(po)
        currentPosition = realPo
        pageIndicatorView.setSelected(realPo)
    }

    fun getRealPosition(po: Int): Int {
        return infiniteAdapter?.getRealPosition(po) ?: -1
    }

    fun setListImage(listImage: MutableList<String>?, type: Int) {
        var listImage = listImage ?: mutableListOf()
        countItem = listImage.count()
        adapterBanner?.setListImage(listImage, type)
        pageIndicatorView.count = countItem
        if (listImage.size > 0) {
            pageIndicatorView.setSelected(0)
            if (isAutoSlide) {
                handleAutoSlide()
            }
        }
    }

    private fun handleAutoSlide() {
//        val speedScroll = Constants.PERIOD_AUTO_SCROLL_SLIDER
//        mHandler = Handler()
//        runnable = object : Runnable {
//            var count = currentPosition
//            override fun run() {
//                if (count == countItem - 1)
//                    count = -1
//                if (count < countItem - 1) {
//                    var destination = infiniteAdapter?.getClosestPosition(++count)
//                    dScrollview.smoothScrollToPosition(
//                        destination ?: 0
//                    )
//                    mHandler?.postDelayed(this, timeSlide.toLong())
//                }
//            }
//        }
//        mHandler?.postDelayed(runnable, timeSlide.toLong())
    }

}