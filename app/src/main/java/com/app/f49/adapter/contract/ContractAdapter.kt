package com.app.f49.adapter.contract

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.databinding.RowContractBinding
import com.app.f49.databinding.RowItemDashboardBinding
import com.app.f49.model.managercontract.ManagerContractDTO
import extension.setOnSingleClickListener


class ContractAdapter(var items: MutableList<ManagerContractDTO>) : RecyclerView.Adapter<ContractAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowContractBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_contract, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<ManagerContractDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowContractBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {

            }
            binding.executePendingBindings()
        }
    }


}


