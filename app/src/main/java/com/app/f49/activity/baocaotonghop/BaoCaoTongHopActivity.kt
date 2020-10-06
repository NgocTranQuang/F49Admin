package com.app.f49.activity.baocaotonghop

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.Base
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityBaocaotonghopBinding
import com.app.f49.fragment.picker.MyDatePickerFragment
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_baocaotonghop.*

class BaoCaoTongHopActivity : BaseMvvmActivity<ActivityBaocaotonghopBinding, BaoCaoTongHopViewModel, BaseNavigator>() {
    var idCuaHang: Int? = 0

    companion object {
        const val KEY_DATA_ID_CUA_HANG = "KEY_DATA_ID_CUA_HANG"
        fun start(context: Context?, idCuaHang: Int?) {
            context?.startActivity(Intent(context, BaoCaoTongHopActivity::class.java).putExtra(KEY_DATA_ID_CUA_HANG, idCuaHang))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_baocaotonghop
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.bao_cao_tong_hop)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataItent()
        mViewModel?.setNavigator(this)
        obsever()
        evenClickListener()
        initSpiner()
    }

    private fun obsever() {
        Base.listStore.observe(this, Observer {
            it?.let {
                spSelectStore.setList(it.map { it.storeName }.toMutableList(), 0)
            }
        })
        mViewModel?.baocaotonghopDTO?.observe(this, Observer {
            mViewBinding?.item = it
        })
//        mViewModel?.fromDate?.observe(this, Observer {
//            if (it == null)
//                tvFromDate.text = getString(R.string.tu_ngay)
//        })
//        mViewModel?.toDate?.observe(this, Observer {
//            if (it == null)
//                tvToDate.text = getString(R.string.den_ngay)
//        })
        mViewBinding?.viewModel = mViewModel
    }

    private fun getDataItent() {
        mViewModel?.getBaoCaoTongHop(idCuaHang)
    }

    private fun evenClickListener() {
        tvFromDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.fromDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.fromDate?.value = it
                mViewModel?.getBaoCaoTongHop(idCuaHang)
            }
        }
        tvToDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.toDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.toDate?.value = it
                mViewModel?.getBaoCaoTongHop(idCuaHang)
            }
        }
//        btnXem.setOnSingleClickListener {
//            mViewModel?.getBaoCaoTongHop(idCuaHang)
//        }
    }

    private fun initSpiner() {
        spSelectStore.selectedItemListener {
            idCuaHang = Base.listStore.value?.get(it)?.id?.toIntOrNull()
            mViewModel?.getBaoCaoTongHop(idCuaHang)
        }
    }
}