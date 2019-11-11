package com.app.f49.adapter.dashboard

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.baocaotonghop.BaoCaoTongHopActivity
import com.app.f49.activity.managerContract.ContractActivity
import com.app.f49.activity.quanlythuchi.QuanLyThuChiActivity
import com.app.f49.activity.rutlaicuahang.RutLaiCuaHangActivity
import com.app.f49.activity.taisanthanhly.TaiSanThanhLyActivity
import com.app.f49.activity.tienhoahong.TienHoaHongActivity
import com.app.f49.databinding.RowItemDashboardBinding
import com.app.f49.model.home.ItemHomeDTO
import extension.setOnSingleClickListener

class DashboardItemAdapter(var items: MutableList<ItemHomeDTO>, var idCuaHang: Int?) : RecyclerView.Adapter<DashboardItemAdapter.ViewHolder>() {
    var heightItem = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowItemDashboardBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.row_item_dashboard, parent, false)
        if (heightItem == 1) {
            heightItem = (parent.measuredHeight - (((parent.context?.resources?.getDimensionPixelSize(R.dimen.height_line_size)
                ?: 1) * 3))) / 4
        }
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 8


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<ItemHomeDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RowItemDashboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val layoutParams = itemView.layoutParams
            layoutParams.height = heightItem
            var item = items.getOrNull(position)
            item?.let {
                binding.item = it
                binding.root.setOnSingleClickListener {
                    when (item.screenId) {
                        ScreenIDEnum.BAO_CAO_TONG_HOP.value -> {
                            BaoCaoTongHopActivity.start(itemView.context, idCuaHang)
                        }
                        ScreenIDEnum.RUT_LAI.value, ScreenIDEnum.RUT_VON.value -> {
                            RutLaiCuaHangActivity.start(itemView.context, item.screenId ?: "")
                        }
                        ScreenIDEnum.QUAN_LY_THU_CHI.value -> {
                            QuanLyThuChiActivity.start(itemView.context)
                        }
                        ScreenIDEnum.HOP_DONG_CAM_DO.value, ScreenIDEnum.CAM_DO_GIA_DUNG.value, ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                            ContractActivity.start(itemView.context, item.screenId)
                        }
                        ScreenIDEnum.TAI_SAN_THANH_LY.value -> {
                            TaiSanThanhLyActivity.start(itemView.context)
                        }
                        ScreenIDEnum.TIEN_HOA_HONG.value -> {
                            TienHoaHongActivity.start(itemView.context)
                        }
                        else -> {

                        }
                    }
                }
                binding.executePendingBindings()
            }

        }
    }


}


