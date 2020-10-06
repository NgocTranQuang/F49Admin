package com.app.f49.adapter.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.app.f49.fragment.dashboard.DashboardPagerItemFragment
import com.app.f49.fragment.home.PagerItemFragment
import com.app.f49.model.home.ItemHomeDTO
import kotlin.math.min

class HomeViewPagerAdapter(var listData: MutableList<ItemHomeDTO>, var fm: FragmentManager, var countPage: Int, var type: Int) : FragmentStatePagerAdapter(fm) {
        var numberItemInPager = 6

    init {
        if (type == TypePager.DASHBOARD.value) {
            numberItemInPager = 8
        }
    }

    fun insertData(list: MutableList<ItemHomeDTO>, countOfPage: Int) {
        listData = list
        countPage = countOfPage
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
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

    override fun getCount(): Int = countPage
}

enum class TypePager(var value: Int) {
    HOME(0), DASHBOARD(1)
}