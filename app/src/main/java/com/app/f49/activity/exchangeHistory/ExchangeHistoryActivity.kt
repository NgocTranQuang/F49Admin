package com.app.f49.activity.exchangeHistory

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.R
import com.app.f49.TypeActionChiTietHDCDEnum
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.adapter.exchangeHistory.BorrowHistoryAdapter
import com.app.f49.adapter.exchangeHistory.ExchangeHistoryAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityExchangehistoryBinding
import com.app.f49.model.history.BorrowHistoryDTO
import com.app.f49.model.history.ExchangeHistoryDTO
import extension.init
import kotlinx.android.synthetic.main.activity_exchangehistory.*

class ExchangeHistoryActivity : BaseMvvmActivity<ActivityExchangehistoryBinding, ExchangeHistoryViewModel, BaseNavigator>() {
    var adapterExchange: ExchangeHistoryAdapter? = null
    var adapterBorrow: BorrowHistoryAdapter? = null
    var typeAction: Int? = null

    companion object {
        val KEY_PASS_DATA = "KEY_PASS_DATA"
        val KEY_SO_HOP_DONG = "KEY_SO_HOP_DONG"
        val KEY_ID_KHACH_HANG = "KEY_ID_KHACH_HANG"
        val KEY_TEN_KHACH_HANG = "KEY_TEN_KHACH_HANG"
        val KEY_TYPE_ACTION = "KEY_TYPE_ACTION"
        fun start(context: Context, idHopDong: Int?, soHopDong: String?, idKhachHang: Int?, tenKH: String?, typeAction: Int) {
            context.startActivity(Intent(context, ExchangeHistoryActivity::class.java).putExtra(KEY_PASS_DATA, idHopDong).putExtra(KEY_SO_HOP_DONG, soHopDong).putExtra(KEY_ID_KHACH_HANG, idKhachHang).putExtra(KEY_TEN_KHACH_HANG, tenKH).putExtra(KEY_TYPE_ACTION, typeAction))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_exchangehistory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRV()
        obsever()
        getData()
    }

    private fun getData() {
        mViewModel?.setNavigator(this)
        var idHopDong = intent?.getIntExtra(KEY_PASS_DATA, 0)
        var idKhachHng = intent?.getIntExtra(KEY_ID_KHACH_HANG, 0)
        var tenKH = intent?.getStringExtra(KEY_TEN_KHACH_HANG)
        var soHopDong = intent?.getStringExtra(KEY_SO_HOP_DONG)
        if (typeAction == TypeActionChiTietHDCDEnum.EXCHANGE.value) {
            mViewModel?.soHopDong?.value = "Số HD: $soHopDong"
            mViewModel?.getListLichSuGiaoDich(idHopDong)
            title = getString(R.string.lich_su_giao_dich)
        } else {
            mViewModel?.soHopDong?.value = "Tên KH: $tenKH"
            mViewModel?.getListLichSuVayNo(idKhachHng)
            title = getString(R.string.lich_su_vay)
        }
    }

    private fun obsever() {
        mViewModel?.listExchangeHistory?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })
        mViewModel?.listBorrowHistory?.observe(this, Observer {
            it?.let {
                insertDataBorrow(it)
            }
        })
    }

    fun insertData(it: MutableList<ExchangeHistoryDTO>) {
        if (it.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        adapterExchange?.setData(it)
    }

    fun insertDataBorrow(it: MutableList<BorrowHistoryDTO>) {
        if (it.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        adapterBorrow?.setData(it)
    }

    private fun initRV() {
        rvExchangeHistory.init(space = R.dimen.height_line_size)
        typeAction = intent?.getIntExtra(KEY_TYPE_ACTION, 0)
        if (typeAction == TypeActionChiTietHDCDEnum.EXCHANGE.value) {
            adapterExchange = ExchangeHistoryAdapter(mutableListOf(), rvExchangeHistory)
            rvExchangeHistory.adapter = adapterExchange
        } else {
            adapterBorrow = BorrowHistoryAdapter(mutableListOf(), rvExchangeHistory)
            rvExchangeHistory.adapter = adapterBorrow
        }
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun showLoading(cancelable: Boolean) {
        tvNoData.visibility = View.GONE
        rvExchangeHistory.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        rvExchangeHistory.visibility = View.VISIBLE
        shimmer.visibility = View.GONE
        shimmer.startShimmer()
    }

}