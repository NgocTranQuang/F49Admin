package com.app.f49.activity.timkiem

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.EditorInfo
import com.app.f49.DateFilterEnum
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityTimkiemBinding
import com.app.f49.model.timkiem.TimKiemDTO
import extension.init
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_timkiem.*


class TimKiemActivity : BaseMvvmActivity<ActivityTimkiemBinding, TimKiemViewModel, BaseNavigator>() {

    var listFiterByDate = DateFilterEnum.getListDateFilter()
    var adapter: TimKiemAdapter? = null
    var idNhanVienCurrent: Int? = null
    var type: String? = ""
    var pageIndex = 1;

    companion object {
        val KEY_PASS_TYPE = "KEY_PASS_TYPE"
        fun start(context: Context) {
            context.startActivity(Intent(context, TimKiemActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_timkiem
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.tim_kiem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRV()
        obsever()
        evenClickListener()

    }

    private fun initRV() {
        rvHopDong.init(space = R.dimen.height_line_size)
        adapter = TimKiemAdapter(mutableListOf(), rvHopDong)
        rvHopDong.adapter = adapter
        mViewModel?.setNavigator(this)
        adapter?.setLoadMoreListener {
            pageIndex += 1;
            getListQuanLyThuChi()
        }

    }

    private fun evenClickListener() {

        cvLogin.setOnSingleClickListener {
            pageIndex = 1
            getListQuanLyThuChi()
        }
        edtKeySearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                pageIndex = 1
                getListQuanLyThuChi()
                true
            } else {
                false
            }
        }

    }

    fun getListQuanLyThuChi() {
        hideKeyboard()
        if (edtKeySearch.text.toString().isNullOrEmpty()) {
            showErrorDialog(getString(R.string.invalid_data))
            return
        }
        mViewModel?.timKiemHopDong(edtKeySearch.text.toString(), pageIndex)
    }

    private fun obsever() {

        mViewModel?.listHopDong?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })


    }

    private fun insertData(list: MutableList<TimKiemDTO>) {
        if (list.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        if (pageIndex == 1) {
            adapter?.listItems?.clear()
            adapter?.setData(list)
        } else {
            adapter?.setData(list)
        }

    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        tvNoData.visibility = View.GONE
        rvHopDong.visibility = View.GONE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        rvHopDong.visibility = View.VISIBLE
        shimmer.stopShimmer()
    }
}