package com.app.f49.activity.taisanthanhly

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityThongtinTaisanthanhlyBinding
import kotlinx.android.synthetic.main.activity_thongtin_taisanthanhly.*

class ThongTinTaiSanThanhLyActivity : BaseMvvmActivity<ActivityThongtinTaisanthanhlyBinding, ThongTinTaiSanThanhLyViewModel, BaseNavigator>() {
    var idItem = 0

    companion object {
        val KEY_PASS_DATA = "KEY_PASS_DATA"
        fun start(context: Context, idItem: Int?) {
            context.startActivity(Intent(context, ThongTinTaiSanThanhLyActivity::class.java).putExtra(KEY_PASS_DATA, idItem))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_thongtin_taisanthanhly
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.thong_tin_thanh_ly_tai_san)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataIntent()
        initViewModel()
    }

    private fun getDataIntent() {
        idItem = intent?.getIntExtra(KEY_PASS_DATA, 0) ?: 0
    }

    private fun initViewModel() {
        mViewModel?.thongTinTaiSanThanhLyDTO?.observe(this, Observer {
            mViewBinding?.item = it
        })
        mViewModel?.getThongTinTaiSanChiTiet(idItem)
    }
}