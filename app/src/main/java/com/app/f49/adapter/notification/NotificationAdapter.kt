package com.app.f49.adapter.notification

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.adapter.LoadMoreAdapter
import com.app.f49.databinding.RowNotificationBinding
import com.app.f49.model.notification.NotificationDTO
import extension.setOnSingleClickListener


class NotificationAdapter(var listItems: MutableList<NotificationDTO>, rv: RecyclerView, var onclickItem: (NotificationDTO, () -> Unit) -> Unit, var onLongclickItem: (NotificationDTO, () -> Unit) -> Unit) : LoadMoreAdapter<NotificationDTO>(listItems, rv) {

    override fun onCreateViewHolderItem(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowNotificationBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_notification, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolderItem(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotificationAdapter.ViewHolder)
            holder.bind(position)
    }

    fun insertData(items: MutableList<NotificationDTO>) {
        setData(items)
    }

    fun changeRealAll() {
        this.items?.forEach { it.daDoc = true }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            var item = items?.getOrNull(position)
            binding.item = item
            binding.root.setOnSingleClickListener {
                items?.get(position)?.id?.let {
                    onclickItem.invoke(items?.getOrNull(position)!!) {
                        items?.getOrNull(position)?.daDoc = true
                        binding.item = items?.getOrNull(position)
                        notifyItemChanged(position)
                    }
                }
            }
            binding.root.setOnLongClickListener {
                onLongclickItem.invoke(items?.getOrNull(position)!!) {
                    items?.removeAt(position)
                    notifyItemRemoved(position)
                }
                true
            }
            binding.executePendingBindings()
        }
    }


}