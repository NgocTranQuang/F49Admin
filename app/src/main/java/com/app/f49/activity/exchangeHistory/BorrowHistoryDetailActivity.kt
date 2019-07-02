package com.app.f49.activity.exchangeHistory

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityBorrowHistoryDetailBinding
import kotlinx.android.synthetic.main.activity_borrow_history_detail.*

class BorrowHistoryDetailActivity : BaseMvvmActivity<ActivityBorrowHistoryDetailBinding, BorrowHistoryViewModel, BaseNavigator>() {
    companion object {
        val KEY_ID_HOP_DONG = "KEY_ID_HOP_DONG"
        fun start(context: Context, idHopDong: Int?) {
            context.startActivity(Intent(context, BorrowHistoryDetailActivity::class.java).putExtra(KEY_ID_HOP_DONG, idHopDong))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_borrow_history_detail
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.chi_tiet_lich_su_vay)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        observer()
    }

    private fun getData() {
        mViewModel?.setNavigator(this)
        var idHopDong = intent.getIntExtra(KEY_ID_HOP_DONG, 0)
        mViewModel?.getChiTietLichSuVayNo(idHopDong)
    }

    private fun observer() {
        mViewModel?.detailBorrowDTO?.observe(this, Observer {
            mViewBinding?.item = it
        })
    }
}