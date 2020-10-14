package com.app.f49.activity.tienhoahong

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.databinding.RowTienhoahongBinding
import com.app.f49.model.nhanvien.HoaHongDTO
import com.app.f49.extension.setOnSingleClickListener

class TienHoaHongAdapter(var items: MutableList<HoaHongDTO.HoaHongItem>) : RecyclerView.Adapter<TienHoaHongAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowTienhoahongBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_tienhoahong, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<HoaHongDTO.HoaHongItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowTienhoahongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
//                QuanLyThuChiDetailActivity.start(itemView.context, items[position].id ?: 10)
                //                clickItem.invoke(position)
            }
            binding.executePendingBindings()
        }
    }


}


