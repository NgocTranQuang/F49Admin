package com.app.f49.adapter.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BaseEqualItemAdapter<T>(var items: MutableList<T>, var numberOfColumns: Int) : RecyclerView.Adapter<BaseEqualItemAdapter<*>.BaseEqualItemViewHolder>() {
    var heightItem = 0
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseEqualItemViewHolder {
        heightItem = p0.measuredHeight / numberOfColumns
        return onCreateViewHolderItem(p0, p1)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract fun onCreateViewHolderItem(p0: ViewGroup, p1: Int): BaseEqualItemViewHolder

    inner class BaseEqualItemViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val layoutParams = itemView.layoutParams
            layoutParams.height = heightItem
        }
    }

}