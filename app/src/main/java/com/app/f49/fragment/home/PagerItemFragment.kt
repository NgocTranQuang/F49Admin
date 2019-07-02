package com.app.f49.fragment.home

import android.os.Bundle
import android.view.View
import com.app.f49.R
import com.app.f49.adapter.home.HomeItemAdapter
import com.app.f49.custom.CustomGridLayoutManager
import com.app.f49.decoration.RVTowColumnDecoration
import com.app.f49.fragment.base.BaseFragment
import com.app.f49.model.home.ItemHomeDTO
import kotlinx.android.synthetic.main.fragment_item_viewpager_home.*
import java.io.Serializable

class PagerItemFragment : BaseFragment() {
    companion object {
        val KEY_DATA = "KEY_DATA"
        fun newInstance(listData: MutableList<ItemHomeDTO>): PagerItemFragment {
            var fg = PagerItemFragment()
            var bundle = Bundle()
            bundle.putSerializable(KEY_DATA, listData as Serializable)
            fg.arguments = bundle
            return fg
        }
    }

    var adapter: HomeItemAdapter? = null
    override fun getLayoutResource(): Int {
        return R.layout.fragment_item_viewpager_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listData = arguments?.getSerializable(KEY_DATA) as MutableList<ItemHomeDTO>
        setupRV(listData)
    }

    private fun setupRV(list: MutableList<ItemHomeDTO>) {
        adapter = HomeItemAdapter(list)
        var layoutManager=CustomGridLayoutManager(activity!!, 2)
        layoutManager.setScrollEnabled(false)
        rvItemHome.layoutManager = layoutManager
        rvItemHome.adapter = adapter
        rvItemHome.addItemDecoration(
            RVTowColumnDecoration(
                context?.resources?.getDimensionPixelSize(R.dimen.activity_horizontal_margin) ?: 24
            )
        )
    }

}