package com.app.f49.adapter.store

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.model.store.StoreDTO
import com.app.f49.extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.row_store.view.*


class ListStoreAdapter(var items: MutableList<StoreDTO>) : RecyclerView.Adapter<ListStoreAdapter.ViewHolder>() {
    var positionSelected = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_store, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<StoreDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(position: Int) {
            var item = items.get(position)
            if (item.isChoose == true) {
                itemView.radioButton.isChecked = true
                positionSelected = position
            } else {
                itemView.radioButton.isChecked = false
            }
            itemView.tvName.text = item.storeName
            binding.setOnSingleClickListener {
                if (position != positionSelected) {
                    items.get(positionSelected).isChoose = false
                    items.get(position).isChoose = true
                    notifyDataSetChanged()
                }
            }
        }
    }


}
