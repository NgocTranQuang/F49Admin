package com.app.f49.fragment.dashboard

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.app.f49.MainViewModel
import com.app.f49.R
import com.app.f49.adapter.dashboard.DashboardItemAdapter
import com.app.f49.custom.CustomGridLayoutManager
import com.app.f49.decoration.DecoWithoutLeftRight
import com.app.f49.fragment.base.BaseFragment
import com.app.f49.model.home.ItemHomeDTO
import kotlinx.android.synthetic.main.fragment_item_viewpager_home.*
import java.io.Serializable


class DashboardPagerItemFragment : BaseFragment() {
    var idCuaHang: Int? = null

    companion object {
        val KEY_DATA = "KEY_DATA"
        val KEY_DATA_CUA_HANG = "KEY_DATA_CUA_HANG"
        fun newInstance(listData: MutableList<ItemHomeDTO>): DashboardPagerItemFragment {
            var fg = DashboardPagerItemFragment()
            var bundle = Bundle()
            bundle.putSerializable(KEY_DATA, listData as Serializable)
            fg.arguments = bundle
            return fg
        }
    }

    var adapter: DashboardItemAdapter? = null
    override fun getLayoutResource(): Int {
        return R.layout.fragment_item_viewpager_dashboard
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listData = (arguments?.getSerializable(KEY_DATA) as MutableList<ItemHomeDTO>).sortedBy {
            it.sapXep
        }.toMutableList()
        var viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        idCuaHang = viewModel.listStore.value?.get(viewModel.currentPositionStore.value ?: 0)?.id?.toIntOrNull()
        setupRV(listData)
    }

    private fun setupRV(list: MutableList<ItemHomeDTO>) {
        adapter = DashboardItemAdapter(list, idCuaHang)
        var layoutManager = CustomGridLayoutManager(activity!!, 2)
        layoutManager.setScrollEnabled(false)
        rvItemHome.layoutManager = layoutManager
        rvItemHome.adapter = adapter
        rvItemHome.addItemDecoration(
            DecoWithoutLeftRight(
                context?.resources?.getDimensionPixelSize(R.dimen.height_line_size) ?: 1
            )
        )
    }

}