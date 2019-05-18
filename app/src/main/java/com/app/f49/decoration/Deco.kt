package com.app.f49.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout

class DividerItemDecoration(private val mSizeSpacingPx: Int, private var mOrientation: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        if (mOrientation == VERTICAL) {
            var bottomHeight = 0
            if (position < itemCount - 1) {
                bottomHeight = mSizeSpacingPx
            }
            outRect.set(0, 0, 0, bottomHeight)
        } else {
            var rightWidth = 0
            if (position < itemCount - 1) {
                rightWidth = mSizeSpacingPx
            }
            outRect.set(0, 0, rightWidth, 0)
        }
    }

    companion object {
        val HORIZONTAL = LinearLayout.HORIZONTAL
        val VERTICAL = LinearLayout.VERTICAL
    }
}

