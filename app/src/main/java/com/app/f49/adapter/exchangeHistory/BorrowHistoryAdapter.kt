package com.app.f49.adapter.exchangeHistory

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.app.f49.activity.exchangeHistory.BorrowHistoryDetailActivity
import com.app.f49.adapter.BaseAdapter
import com.app.f49.databinding.RowBorrowHistoryBinding
import com.app.f49.model.history.BorrowHistoryDTO

class BorrowHistoryAdapter(var listItem: MutableList<BorrowHistoryDTO>, var rec: RecyclerView) : BaseAdapter<RowBorrowHistoryBinding, BorrowHistoryDTO>(listItem, rec) {
    override fun initModel(item: BorrowHistoryDTO?, position: Int) {
        item?.stt = "${position + 1}"
    }

    override fun onEventClickListener(context: Context, item: BorrowHistoryDTO?) {
        super.onEventClickListener(context, item)
        BorrowHistoryDetailActivity.start(context, item?.id)
    }
}