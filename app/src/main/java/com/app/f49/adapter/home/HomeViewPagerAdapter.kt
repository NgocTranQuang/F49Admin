package com.app.f49.adapter.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.app.f49.fragment.dashboard.DashboardPagerItemFragment
import com.app.f49.fragment.home.PagerItemFragment
import com.app.f49.model.home.ItemHomeDTO
import kotlin.math.max
import kotlin.math.min

class HomeViewPagerAdapter(var listData: MutableList<ItemHomeDTO>, var fm: FragmentManager, var type: Int) : FragmentPagerAdapter(fm) {
    var numberItemInPager = 6

    init {
        if (type == TypePager.DASHBOARD.value) {
            numberItemInPager = 8
        }
    }

    override fun getItem(p0: Int): Fragment {

        var min = min(p0 * numberItemInPager + numberItemInPager, listData.size)
        var b: MutableList<ItemHomeDTO> = mutableListOf()
        b.addAll(listData.subList((p0 * numberItemInPager), min))
        if (type == TypePager.HOME.value) {

            return PagerItemFragment.newInstance(b)
        } else {

            return DashboardPagerItemFragment.newInstance(b)
        }
    }

    override fun getCount(): Int = (Math.ceil((listData.size).toDouble() / numberItemInPager)).toInt()
}

enum class TypePager(var value: Int) {
    HOME(0), DASHBOARD(1)
}