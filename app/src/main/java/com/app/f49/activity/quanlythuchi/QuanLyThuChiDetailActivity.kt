package com.app.f49.activity.quanlythuchi

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityQuanlythuchiDetailBinding
import kotlinx.android.synthetic.main.activity_quanlythuchi_detail.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class QuanLyThuChiDetailActivity : BaseMvvmActivity<ActivityQuanlythuchiDetailBinding, QuanLyThuChiDetailViewModel, BaseNavigator>() {

    companion object {
        val KEY_ID_ITEM = "KEY_ID_ITEM"
        fun start(context: Context, idItem: Int) {
            context.startActivity(Intent(context, QuanLyThuChiDetailActivity::class.java).putExtra(KEY_ID_ITEM, idItem))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_quanlythuchi_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel?.setNavigator(this)
        getExtra()
        obsever()
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.thong_tin_thu_chi)
    }
    private fun obsever() {
        mViewModel?.quanLyThuChiDetailDTO?.observe(this, Observer {
            mViewBinding?.item = it
        })
    }

    private fun getExtra() {
        var idItem = intent?.getIntExtra(KEY_ID_ITEM, 0)
        idItem?.let {
            getData(it)
        }
    }

    private fun getData(id: Int?) {
        mViewModel?.getDetailQuanLyThuChi(id)
    }
}