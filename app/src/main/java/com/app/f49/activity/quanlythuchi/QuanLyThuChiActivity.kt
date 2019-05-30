package com.app.f49.activity.quanlythuchi

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.Base
import com.app.f49.DateFilterEnum
import com.app.f49.R
import com.app.f49.databinding.ActivityQuanlythuchiBinding
import extension.init
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_quanlythuchi.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.picker.MyDatePickerFragment

class QuanLyThuChiActivity : BaseMvvmActivity<ActivityQuanlythuchiBinding, QuanLyThuChiViewModel, BaseNavigator>() {
    var listFiterByDate = DateFilterEnum.getListDateFilter()
    var adapter: QuanLyThuChiAdapter? = null
    var idStoreCurrent: String? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, QuanLyThuChiActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_quanlythuchi
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.quan_ly_thu_chi)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRV()
        obsever()
        initSpiner()
        evenClickListener()

    }

    private fun initRV() {
        rvQuanLyThuChi.init(space = R.dimen.height_line_size)
        adapter = QuanLyThuChiAdapter(mutableListOf())
        rvQuanLyThuChi.adapter = adapter
        mViewModel?.setNavigator(this)
    }

    private fun evenClickListener() {
        tvFromDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.fromDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.fromDate?.value = it
            }
        }
        tvToDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.toDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.toDate?.value = it
            }
        }
    }

    private fun initSpiner() {
        spSelectStore.selectedItemListener {
            idStoreCurrent = Base.listStore.value?.get(it)?.id
            getListQuanLyThuChi()
        }
        spSelectDay.selectedItemListener {
            mViewModel?.fromDate?.value = DateFilterEnum.get(it)?.getStartDate()
            mViewModel?.toDate?.value = DateFilterEnum.get(it)?.getEndDate()
            mViewModel?.clickable?.value = it == DateFilterEnum.FROM_TO.value
            getListQuanLyThuChi()
        }
        spSelectDay.setList(listFiterByDate.map { it.getNameDate() }.toMutableList(), 0)
    }

    fun getListQuanLyThuChi() {
        mViewModel?.getListQuanLyThuChi(idStoreCurrent?.toIntOrNull())
    }

    private fun obsever() {
        Base.listStore.observe(this, Observer {
            it?.let {
                spSelectStore.setList(it.map { it.storeName }.toMutableList(), 0)
            }
        })

        mViewModel?.listQuanLyThuChi?.observe(this, Observer {
            it?.let {
                adapter?.insertData(it)
            }
        })
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