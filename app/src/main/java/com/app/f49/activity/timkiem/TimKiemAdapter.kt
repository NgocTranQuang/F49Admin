package com.app.f49.activity.timkiem

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.infoContract.InfoContractActivity
import com.app.f49.adapter.LoadMoreAdapter
import com.app.f49.databinding.RowTimkiemBinding
import com.app.f49.model.timkiem.TimKiemDTO
import com.app.f49.extension.setOnSingleClickListener

//class TimKiemAdapter(var items: MutableList<TimKiemDTO>) : RecyclerView.Adapter<TimKiemAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding: RowTimkiemBinding =
//            DataBindingUtil.inflate(layoutInflater, R.layout.row_timkiem, parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(position)
//    }
//
//    fun insertData(items: MutableList<TimKiemDTO>) {
//        this.items = items
//        notifyDataSetChanged()
//    }
//
//    inner class ViewHolder(val binding: RowTimkiemBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(position: Int) {
//            items[position].stt = "${position + 1}"
//            binding.item = items[position]
//            binding.root.setOnSingleClickListener {
////                QuanLyThuChiDetailActivity.start(itemView.context, items[position].id ?: 10)
//                //                clickItem.invoke(position)
//            }
//            binding.executePendingBindings()
//        }
//    }
//
//
//}
class TimKiemAdapter(var listItems: MutableList<TimKiemDTO>, recyclerView: RecyclerView) : LoadMoreAdapter<TimKiemDTO>(listItems, recyclerView) {


    override fun onBindViewHolderItem(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TimKiemAdapter.ViewHolder)
            holder.bind(position)
    }

    override fun onCreateViewHolderItem(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowTimkiemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_timkiem, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listItems?.size ?: 0


//    fun insertData(items: MutableList<TimKiemDTO>) {
//        this.listItems.addAll(items)
//        notifyDataSetChanged()
//    }

    inner class ViewHolder(val binding: RowTimkiemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            listItems[position].stt = "${position + 1}"
            binding.item = listItems[position]
            binding.root.setOnSingleClickListener {
                InfoContractActivity.start(itemView.context, listItems[position].id.toString()
                    ?: "", ScreenIDEnum.HOP_DONG_CAM_DO.value, null)

                //                QuanLyThuChiDetailActivity.start(itemView.context, items[position].id ?: 10)
                //                clickItem.invoke(position)
            }
            binding.executePendingBindings()
        }
    }


}


