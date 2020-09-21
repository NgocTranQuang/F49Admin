package com.app.f49.activity.quanlythuchi

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.databinding.RowQuanlythuchiBinding
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
import extension.setOnSingleClickListener

class QuanLyThuChiAdapter(var items: MutableList<QuanLyThuChiDTO>) : RecyclerView.Adapter<QuanLyThuChiAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowQuanlythuchiBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_quanlythuchi, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<QuanLyThuChiDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowQuanlythuchiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                QuanLyThuChiDetailActivity.start(itemView.context, items[position].id ?: 10,null)
                //                clickItem.invoke(position)
            }
            binding.executePendingBindings()
        }
    }


}


