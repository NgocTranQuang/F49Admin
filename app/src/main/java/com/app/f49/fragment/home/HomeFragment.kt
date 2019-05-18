package com.app.f49.fragment.home

import android.os.Bundle
import android.view.View
import com.app.f49.Base
import com.app.f49.adapter.home.HomeViewPagerAdapter
import com.app.f49.databinding.FragmentHomeBinding
import com.app.f49.model.home.ItemHomeDTO
import kotlinx.android.synthetic.main.fragment_home.*
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.base.BaseMvvmFragment
import android.widget.ArrayAdapter
import com.app.f49.activity.camdo.CamdoActivity
import com.app.f49.adapter.home.TypePager
import extension.setOnSingleClickListener
import android.support.v4.view.ViewPager.OnPageChangeListener
import com.app.f49.TypeHeader
import extension.setList


class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel, BaseNavigator>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpager.adapter = HomeViewPagerAdapter(getListData(), childFragmentManager!!, TypePager.HOME.value)
        setSpiner()
        pageIndicatorView.count = (Math.ceil((getListData().size).toDouble() / 6)).toInt()
        setEventClick()
    }

    private fun setEventClick() {
        btnCamdo.setOnSingleClickListener {
            CamdoActivity.start(activity ?: return@setOnSingleClickListener, TypeHeader.CAM_DO.value)
        }
        btnDinhGia.setOnSingleClickListener {
            CamdoActivity.start(activity ?: return@setOnSingleClickListener, TypeHeader.DINH_GIA.value)
        }
        btnDoGiaDung.setOnSingleClickListener {
            CamdoActivity.start(activity ?: return@setOnSingleClickListener, TypeHeader.DO_GIA_DUNG.value)
        }
        vpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
                pageIndicatorView.setSelected(position)
            }
        })
    }

    fun getListData(): MutableList<ItemHomeDTO> {
        var list = mutableListOf<ItemHomeDTO>(ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            price = "12,111vnd"
            priceColor = "#50b748"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            price = "12,111vnd"
            priceColor = "#e30000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            price = "12,111vnd"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        }, ItemHomeDTO().apply {
            id = "d"
            imageURL = Base.IMAGE_URL
            title = "thu hoach"
            notification = "12"
            priceColor = "#FFF000"
        })

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
        spinner2.setList(list)

    }
}