package com.app.f49.fragment.dashboard

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.app.f49.Base
import com.app.f49.adapter.home.HomeViewPagerAdapter
import com.app.f49.databinding.FragmentHomeBinding
import com.app.f49.model.home.ItemHomeDTO
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.base.BaseMvvmFragment
import android.widget.ArrayAdapter
import com.app.f49.adapter.home.TypePager
import com.app.f49.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashBoardFragment : BaseMvvmFragment<FragmentDashboardBinding, DashBoardViewModel, BaseNavigator>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpager.adapter = HomeViewPagerAdapter(getListData(), childFragmentManager!!,TypePager.DASHBOARD.value)
        setSpiner()
        pageIndicatorView.count = (Math.ceil((getListData().size).toDouble() / 8)).toInt()

        vpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
                pageIndicatorView.setSelected(position)
            }
        })
    }

    fun getListData(): MutableList<ItemHomeDTO> {
        var list = mutableListOf<ItemHomeDTO>()
        list.add(ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            price = "40,000,000 vnd"
            notification = "12"
            priceColor = "#FFF000"
        })
        list.addAll(list)
        list.addAll(list)
        list.addAll(list)
        list.addAll(list)
        return list
    }

    fun setSpiner() {
        val list = ArrayList<String>()
        list.add("list 1")
        list.add("list 2")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        list.add("list 3")
        val dataAdapter = ArrayAdapter<String>(activity,
            android.R.layout.simple_spinner_item, list)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = dataAdapter

    }
}