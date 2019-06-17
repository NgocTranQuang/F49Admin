package com.app.f49.adapter.notification

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.databinding.RowNotificationBinding
import com.app.f49.model.notification.NotificationDTO
import extension.setOnSingleClickListener


class NotificationAdapter(var items: MutableList<NotificationDTO>, var onclickItem: (NotificationDTO, () -> Unit) -> Unit, var onLongclickItem: (NotificationDTO, () -> Unit) -> Unit) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowNotificationBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_notification, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<NotificationDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun changeRealAll() {
        this.items?.forEach { it.daDoc = true }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                items.get(position).id?.let {
                    onclickItem.invoke(items[position]) {
                        items[position].daDoc = true
                        binding.item = items[position]
                        notifyItemChanged(position)
                    }
                }
            }
            binding.root.setOnLongClickListener {
                onLongclickItem.invoke(items[position]) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
                true
            }
            binding.executePendingBindings()
        }
    }


}