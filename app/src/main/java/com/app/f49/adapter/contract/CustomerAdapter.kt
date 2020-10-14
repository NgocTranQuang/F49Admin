package com.app.f49.adapter.contract

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.model.createcontract.KhachHangDTO
import kotlinx.android.synthetic.main.row_khachhang.view.*

class CustomerAdapter(var items: MutableList<KhachHangDTO>) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    var onClick: ((KhachHangDTO?) -> (Unit))? = null
    fun insertData(items: MutableList<KhachHangDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.row_khachhang, p0, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items.getOrNull(p1)
        if (item != null) {
            p0.binding(item, p1)
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(item: KhachHangDTO, position: Int) {
            itemView.apply {
                tvSTT.text = (position + 1).toString()
                tvHoTen.text = item.hoTen
                tvCMND.text = item.soCMND
                tvSDT.text = item.dienThoai
                tvQueQuan.text = item.queQuan
            }
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val item = items.getOrNull(position)
                onClick?.invoke(item)
            }
        }
    }
}