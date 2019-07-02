package com.app.f49.activity.exchangeHistory

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityExchangeHistoryDetailBinding
import kotlinx.android.synthetic.main.activity_exchange_history_detail.*

class ExchangeHistoryDetailActivity : BaseMvvmActivity<ActivityExchangeHistoryDetailBinding, ExchangeHistoryDetailViewModel, BaseNavigator>() {
    companion object {
        val KEY_ID_HOP_DONG = "KEY_ID_HOP_DONG"
        val KEY_ID_GIAO_DICH = "KEY_ID_GIAO_DICH"
        fun start(context: Context, idHopDong: Int?, idGiaoDich: Int?) {
            var intent = Intent(context, ExchangeHistoryDetailActivity::class.java)
            intent.putExtra(KEY_ID_GIAO_DICH, idGiaoDich)
            intent.putExtra(KEY_ID_HOP_DONG, idHopDong)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_exchange_history_detail
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.chi_tiet_lich_su_giao_dich)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        observer()
    }

    private fun getData() {
        var idHopDong = intent.getIntExtra(KEY_ID_HOP_DONG, 0)
        var idGiaoDich = intent.getIntExtra(KEY_ID_GIAO_DICH, 0)
        mViewModel?.setNavigator(this)
        mViewModel?.getChiTietLichSuGiaoDich(idGiaoDich, idHopDong)
    }

    fun observer() {
        mViewModel?.detailExchange?.observe(this, Observer {
            it?.let {
                mViewBinding?.item = it
            }
        })
    }
}