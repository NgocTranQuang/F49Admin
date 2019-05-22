package com.app.f49.adapter.camdo

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.TypeHeader
import com.app.f49.activity.infocamdo.InfoCamdoActivity
import com.app.f49.activity.infocamdo.InfoDinhGiaActivity
import com.app.f49.activity.infocamdo.InfoDoGiaDungActivity
import com.app.f49.databinding.RowCamdoBinding
import com.app.f49.databinding.RowDinhgiaBinding
import com.app.f49.databinding.RowDogiadungBinding
import com.app.f49.model.dinhgia.CamdoDTO
import extension.setOnSingleClickListener


class CamdoAdapter(var items: MutableList<CamdoDTO>, var typeHeader: Int) : RecyclerView.Adapter<CamdoAdapter.BaseHeader>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHeader {
        if (typeHeader == TypeHeader.CAM_DO.value) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: RowCamdoBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_camdo, parent, false)

            return CamDoViewHolder(binding)
        } else if (typeHeader == TypeHeader.DINH_GIA.value) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: RowDinhgiaBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_dinhgia, parent, false)

            return DinhGiaViewHolder(binding)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: RowDogiadungBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_dogiadung, parent, false)

            return DoGiaDungViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: BaseHeader, position: Int) {
        holder.bind(position)
    }

    fun insertData(items: MutableList<CamdoDTO>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class CamDoViewHolder(val binding: RowCamdoBinding) : BaseHeader(binding) {
        override fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                InfoCamdoActivity.start(itemView.context,items[position])
            }
            binding.executePendingBindings()
        }
    }

    inner class DinhGiaViewHolder(val binding: com.app.f49.databinding.RowDinhgiaBinding) : BaseHeader(binding) {
        override fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                InfoDinhGiaActivity.start(itemView.context,items[position])
            }
            binding.executePendingBindings()
        }
    }

    inner class DoGiaDungViewHolder(val binding: RowDogiadungBinding) : BaseHeader(binding) {
        override fun bind(position: Int) {
            items[position].stt = "${position + 1}"
            binding.item = items[position]
            binding.root.setOnSingleClickListener {
                InfoDoGiaDungActivity.start(itemView.context,items[position])
            }
            binding.executePendingBindings()
        }
    }

    abstract inner class BaseHeader(var bin: ViewDataBinding) : RecyclerView.ViewHolder(bin.root) {
        abstract fun bind(position: Int)

    }

}
