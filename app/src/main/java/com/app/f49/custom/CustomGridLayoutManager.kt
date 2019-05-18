package com.app.f49.custom

import android.content.Context
import android.support.v4.view.ViewCompat.canScrollVertically
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager


class CustomGridLayoutManager(context: Context,var numberColumn: Int) : GridLayoutManager(context,numberColumn) {
    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically()
    }
}