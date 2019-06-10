package com.app.f49.activity.rutlaicuahang

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityThongtinRutlaiBinding
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_thongtin_rutlai.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class ThongTinRutLaiCuaHangActivity : BaseMvvmActivity<ActivityThongtinRutlaiBinding, ThongTinRutLaiViewModel, BaseNavigator>() {
    var idItem: String? = null

    companion object {
        val KEY_PASS_DATA = "KEY_PASS_DATA"
        fun start(context: Context, id: String?) {
            (context as Activity).startActivityForResult(Intent(context, ThongTinRutLaiCuaHangActivity::class.java).putExtra(KEY_PASS_DATA, id), RutLaiCuaHangActivity.REQUEST_CODE)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_thongtin_rutlai
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.thong_tin_rut_lai)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        getIntentData()
        obsever()
        eventClickListener()
    }

    private fun eventClickListener() {
        btnTuChoi.setOnSingleClickListener {
            handleAction(false)
        }
        btnDone.setOnSingleClickListener {
            handleAction(true)
        }
    }

    fun handleAction(isAccept: Boolean) {
        var yKien = edtYKien.text.toString()
        if (yKien.isNullOrBlank()) {
            showErrorDialog(getString(R.string.provide_ykien))
        } else {
            mViewModel?.duyetRutLai(edtYKien.text.toString(), isAccept)
        }
    }

    private fun obsever() {
        mViewModel?.thongTinRutLai?.observe(this, Observer {
            it?.let {
                mViewBinding?.item = it
            }
        })
        mViewModel?.finished?.observe(this, Observer {
            var intent = Intent()
            intent.putExtra(RutLaiCuaHangActivity.KEY_CHECK_RELOAD, true)
            setResult(RESULT_OK, intent);
            finish()
        })
    }

    private fun getIntentData() {
        idItem = intent?.getStringExtra(KEY_PASS_DATA) ?: "1"
        getData()
    }

    private fun getData() {
        mViewModel?.getThongTinRutLaiChiTiet(idItem)
    }

    private fun initViewModel() {
        mViewModel?.setNavigator(this)
    }
}