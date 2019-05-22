package com.app.f49.adapter.home

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.activity.managerContract.ContractActivity
import com.app.f49.databinding.RowItemHomeBinding
import com.app.f49.model.home.ItemHomeDTO
import extension.setOnSingleClickListener

class HomeItemAdapter(var items: MutableList<ItemHomeDTO>) : RecyclerView.Adapter<HomeItemAdapter.ViewHolder>() {
    var heightItem = 1
    val THU_TRONG_THANG = "ThuTrongThang"
    val CHI_TRONG_THANG = "ChiTrongThang"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowItemHomeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_item_home, parent, false)
        if (heightItem == 1) {
            heightItem = (parent.measuredHeight - ((((parent.context?.resources?.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
                ?: 16) + (parent.context?.resources?.getDimensionPixelSize(R.dimen.card_elevation) ?: 5)) * 3))) / 3
        }
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

    inner class ViewHolder(val binding: RowItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            var item = items[position]
            if (item.screenId == THU_TRONG_THANG || item.screenId == CHI_TRONG_THANG) {
                item.price = item.functionValue + " Đ"
                item.functionValue ="0"
            }
            binding.item = items[position]
            val layoutParams = itemView.layoutParams
            layoutParams.height = heightItem
            binding.root.setOnSingleClickListener {
                ContractActivity.start(itemView.context)
            }
            binding.executePendingBindings()
        }
    }


}
