package com.app.f49.fragment.home

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.View
import com.app.f49.TypeHeader
import com.app.f49.activity.camdo.CamdoActivity
import com.app.f49.adapter.home.HomeViewPagerAdapter
import com.app.f49.adapter.home.TypePager
import com.app.f49.databinding.FragmentHomeBinding
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.base.BaseMvvmFragment


class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel, BaseNavigator>() {
    var homeViewPagerAdapter: HomeViewPagerAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.setNavigator(this)
        observer()
        setSpiner()
        setEventClick()

    }

    override fun showLoading(cancelable: Boolean) {
        spinner2.isClickable = false
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        vpager.visibility = View.GONE

    }

    override fun hideLoading() {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        vpager.visibility = View.VISIBLE
        spinner2.isClickable = true
    }

    private fun observer() {
        viewModel?.listItemHome?.observe(this, Observer {
            it?.let {
                var countOfPager = (Math.ceil((it.size).toDouble() / 6)).toInt()
                if (homeViewPagerAdapter == null) {
                    homeViewPagerAdapter = HomeViewPagerAdapter(it, childFragmentManager
                        ?: return@Observer, countOfPager, TypePager.HOME.value)
                }
                vpager.adapter = homeViewPagerAdapter
                pageIndicatorView.count = countOfPager
            }
        })
        getMainViewModel()?.topMenu?.observe(this, Observer {
            it?.let {
                viewBinding?.item = it

            }
        })

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

    private fun setSpiner() {
        getMainViewModel()?.listStore?.observe(this, Observer {
            spinner2.setList((it?.map { it.storeName })?.toMutableList(), getMainViewModel()?.currentPositionStore?.value)
        })
        spinner2.selectedItemListener(Color.WHITE) { position ->
            var idStoreChoose = getMainViewModel()?.listStore?.value?.get(position)
            idStoreChoose?.let {
                getMainViewModel()?.currentPositionStore?.value = position
                viewModel?.getListItemHome(it.id ?: return@selectedItemListener)
            }
        }
    }
}