package com.app.f49.adapter.rutlai

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.activity.rutlaicuahang.ThongTinRutLaiCuaHangActivity
import com.app.f49.databinding.RowRutlaiBinding
import com.app.f49.model.rutlai.RutLaiDTO
import extension.setOnSingleClickListener


class RutLaiAdapter(var items: MutableList<RutLaiDTO>) : RecyclerView.Adapter<RutLaiAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowRutlaiBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_rutlai, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<RutLaiDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowRutlaiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                ThongTinRutLaiCuaHangActivity.start(itemView.context, items[position].idItem ?: "1")
            }
            binding.executePendingBindings()
        }
    }


}


