package com.app.f49.fragment.dashboard

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.app.f49.TypeHeader
import com.app.f49.activity.camdo.CamdoActivity
import com.app.f49.adapter.home.HomeViewPagerAdapter
import com.app.f49.adapter.home.TypePager
import com.app.f49.databinding.FragmentDashboardBinding
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_dashboard.*
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.base.BaseMvvmFragment


class DashBoardFragment : BaseMvvmFragment<FragmentDashboardBinding, DashBoardViewModel, BaseNavigator>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        eventClick()
    }

    private fun observer() {
        viewModel?.setNavigator(this)
        getMainViewModel()?.listStore?.observe(this, Observer {
            spinner2.setList(it?.map { it.storeName }?.toMutableList(), getMainViewModel()?.currentPositionStore?.value)
        })
        viewModel?.listItemDashBoard?.observe(this, Observer {
            it?.let {
                    var countOfPager = (Math.ceil((it.size).toDouble() / 8)).toInt()
                    vpager.adapter = HomeViewPagerAdapter(it, childFragmentManager!!, countOfPager, TypePager.DASHBOARD.value)
                    pageIndicatorView.count = countOfPager


            }
        })
    }

    private fun eventClick() {
        btnCamdo.setOnSingleClickListener {
            CamdoActivity.start(activity ?: return@setOnSingleClickListener, TypeHeader.CAM_DO.value)
        }
        btnDinhGia.setOnSingleClickListener {
            CamdoActivity.start(activity ?: return@setOnSingleClickListener, TypeHeader.DINH_GIA.value)
        }
        btnDoGiaDung.setOnSingleClickListener {
            CamdoActivity.start(activity ?: return@setOnSingleClickListener, TypeHeader.DO_GIA_DUNG.value)
        }
        vpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                pageIndicatorView.setSelected(position)
            }
        })
        spinner2.selectedItemListener(Color.WHITE) { position ->
            var idStoreChoose = getMainViewModel()?.listStore?.value?.get(position)
            idStoreChoose?.let {
                getMainViewModel()?.currentPositionStore?.value = position
                viewModel?.getListItemHome(it.id ?: return@selectedItemListener)
            }

        }
    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        shimmer.stopShimmer()
    }

}