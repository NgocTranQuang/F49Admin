package com.app.f49.adapter.dashboard

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.adapter.base.BaseEqualItemAdapter
import com.app.f49.databinding.RowItemDashboardBinding
import com.app.f49.model.home.ItemHomeDTO
import extension.setOnSingleClickListener

class DashboardItemAdapter(var items: MutableList<ItemHomeDTO>) : RecyclerView.Adapter<DashboardItemAdapter.ViewHolder>() {
    var heightItem = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowItemDashboardBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_item_dashboard, parent, false)
        heightItem = (parent.measuredHeight -(((parent.context?.resources?.getDimensionPixelSize(R.dimen.height_line_size) ?: 1) * 3))) / 4
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<ItemHomeDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowItemDashboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.item = items[position]
            val layoutParams = itemView.layoutParams
            layoutParams.height = heightItem
            binding.root.setOnSingleClickListener {

            }
            binding.executePendingBindings()
        }
    }


}


