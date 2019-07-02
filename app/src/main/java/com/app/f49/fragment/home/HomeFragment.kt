package com.app.f49.fragment.home

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.View
import com.app.f49.R
import com.app.f49.TypeHeader
import com.app.f49.activity.camdo.CamdoActivity
import com.app.f49.adapter.home.HomeViewPagerAdapter
import com.app.f49.adapter.home.TypePager
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.FragmentHomeBinding
import com.app.f49.fragment.base.BaseMvvmFragment
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvvmFragment<FragmentHomeBinding, HomeViewModel, BaseNavigator>() {
    var homeViewPagerAdapter: HomeViewPagerAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.setNavigator(this)

        observer()
        setSpiner()
        setEventClick()

    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
    }
    override fun showLoading(cancelable: Boolean) {
        spStore.isClickable = false
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        vpager.visibility = View.GONE

    }

    override fun hideLoading() {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        vpager.visibility = View.VISIBLE
        spStore.isClickable = true
    }

    override fun getMyToolbar(): View? {
        return lnToolbar
    }
    private fun observer() {
        viewModel?.listItemHome?.observe(this, Observer {
            it?.let {
                var countOfPager = (Math.ceil((it.size).toDouble() / 6)).toInt()
                if (homeViewPagerAdapter == null) {
                    homeViewPagerAdapter = HomeViewPagerAdapter(it, childFragmentManager
                        ?: return@Observer, countOfPager, TypePager.HOME.value)
                    vpager.adapter = homeViewPagerAdapter
                } else {
                    homeViewPagerAdapter?.insertData(it, countOfPager)
                }
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
            spStore.setList((it?.map { it.storeName })?.toMutableList(), getMainViewModel()?.currentPositionStore?.value)
        })
        spStore.selectedItemListener(Color.WHITE) { position ->
            var idStoreChoose = getMainViewModel()?.listStore?.value?.get(position)
            idStoreChoose?.let {
                getMainViewModel()?.currentPositionStore?.value = position
                viewModel?.getListItemHome(it.id ?: return@selectedItemListener)
            }
        }
    }
}