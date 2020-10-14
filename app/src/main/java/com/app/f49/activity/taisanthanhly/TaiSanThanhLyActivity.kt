package com.app.f49.activity.taisanthanhly

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.adapter.taisanthanhly.TaiSanThanhLyAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityTaisanthanhlyBinding
import com.app.f49.extension.init
import com.app.f49.model.taisanthanhly.TaiSanDTO
import com.app.f49.extension.selectedItemListener
import com.app.f49.extension.setList
import kotlinx.android.synthetic.main.activity_taisanthanhly.*

class TaiSanThanhLyActivity : BaseMvvmActivity<ActivityTaisanthanhlyBinding, TaiSanThanhLyViewModel, BaseNavigator>() {
    var countOfInit = 0
    var adapter: TaiSanThanhLyAdapter? = null
    var idNhomTaiSanCurrent: Int? = null
    var idVatCamDoCurrent: Int? = null
    var trangThaiCurrent: Int? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TaiSanThanhLyActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_taisanthanhly
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.tai_san_thanh_ly)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSpinner()
        initRV()
        getData()
        obsever()
    }

    private fun initRV() {
        rvThanhLyTaiSan.init(space = R.dimen.height_line_size)
        adapter = TaiSanThanhLyAdapter(mutableListOf())
        rvThanhLyTaiSan.adapter = adapter
    }

    private fun getData() {
        mViewModel?.getListNhomTaiSan()
        mViewModel?.getListTrangThaiTaiSan()
    }

    private fun obsever() {
        mViewModel?.listNhomTaiSan?.observe(this, Observer {
            spNhomTaiSan.setList(it?.map { it.tenNhom }?.toMutableList(), 0)
        })
        mViewModel?.listTenTaiSan?.observe(this, Observer {
            spTenTaiSan.setList(it?.map { it.tenVatCamCo }?.toMutableList(), 0)
        })
        mViewModel?.listTrangThaiTaiSan?.observe(this, Observer {
            spTrangThaiTaiSan.setList(it?.map { it.tenTrangThai }?.toMutableList(), 0)
        })
        mViewModel?.listTaiSan?.observe(this, Observer {
            insertData(it)
        })
    }

    private fun insertData(list: MutableList<TaiSanDTO>?) {
        var list = list ?: mutableListOf()
        if (list.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        adapter?.insertData(list)
    }

    private fun getListTaiSan() {
        synchronized(this) {
            countOfInit++
            if (countOfInit >= 2) {
                mViewModel?.getListTaiSanThanhLy(idNhomTaiSanCurrent, idVatCamDoCurrent, trangThaiCurrent)
            }
        }
    }

    override fun showLoading(cancelable: Boolean) {
        tvNoData.visibility = View.GONE
        rvThanhLyTaiSan.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        rvThanhLyTaiSan.visibility = View.VISIBLE
        shimmer.visibility = View.GONE
        shimmer.stopShimmer()
    }
    private fun initSpinner() {
        mViewModel?.setNavigator(this)
        spNhomTaiSan.selectedItemListener {
            idNhomTaiSanCurrent = mViewModel?.listNhomTaiSan?.value?.getOrNull(it)?.id
            mViewModel?.getListTenTaiSan(idNhomTaiSanCurrent)
        }
        spTenTaiSan.selectedItemListener {
            idVatCamDoCurrent = mViewModel?.listTenTaiSan?.value?.getOrNull(it)?.id
            getListTaiSan()
        }
        spTrangThaiTaiSan.selectedItemListener {
            trangThaiCurrent = mViewModel?.listTrangThaiTaiSan?.value?.getOrNull(it)?.id
            getListTaiSan()
        }
    }
}