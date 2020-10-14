package com.app.f49.adapter.taisanthanhly

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.activity.taisanthanhly.ThongTinTaiSanThanhLyActivity
import com.app.f49.databinding.RowTaisanthanhlyBinding
import com.app.f49.model.taisanthanhly.TaiSanDTO
import com.app.f49.extension.setOnSingleClickListener


class TaiSanThanhLyAdapter(var items: MutableList<TaiSanDTO>) : RecyclerView.Adapter<TaiSanThanhLyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowTaisanthanhlyBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_taisanthanhly, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<TaiSanDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowTaisanthanhlyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                ThongTinTaiSanThanhLyActivity.start(itemView.context, items[position].id)
            }
            binding.executePendingBindings()
        }
    }
}