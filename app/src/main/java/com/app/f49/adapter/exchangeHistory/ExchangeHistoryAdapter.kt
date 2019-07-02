package com.app.f49.adapter.exchangeHistory

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.app.f49.activity.exchangeHistory.ExchangeHistoryDetailActivity
import com.app.f49.adapter.BaseAdapter
import com.app.f49.databinding.RowExchangeHistoryBinding
import com.app.f49.model.history.ExchangeHistoryDTO

class ExchangeHistoryAdapter(var listItem: MutableList<ExchangeHistoryDTO>, var rec: RecyclerView) : BaseAdapter<RowExchangeHistoryBinding, ExchangeHistoryDTO>(listItem, rec) {
    override fun initModel(item: ExchangeHistoryDTO?, position: Int) {
        item?.stt = "${position +1}"
    }

    override fun onEventClickListener(context : Context,item: ExchangeHistoryDTO?) {
        ExchangeHistoryDetailActivity.start(context,item?.idHopDong,item?.id)
    }
}