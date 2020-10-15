package com.app.f49.adapter.contract

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.model.createcontract.PropertiesImageDTO
import com.app.f49.model.createcontractother.PropertiesCollateralDTO
import kotlinx.android.synthetic.main.row_collateral_other.view.*

class CollateralOtherContractAdapter(var listCollateral: MutableList<PropertiesCollateralDTO>) : RecyclerView.Adapter<CollateralOtherContractAdapter.ViewHolder>() {
    var onClick: ((PropertiesCollateralDTO?) -> (Unit))? = null
    fun insertData(collateral: PropertiesCollateralDTO) {
        this.listCollateral.add(collateral)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.row_collateral_other, p0, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = listCollateral.getOrNull(p1)
        if (item != null) {
            p0.binding(item,p1)
        }
    }

    override fun getItemCount() = listCollateral.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(item: PropertiesCollateralDTO, position: Int) {
            itemView.apply {
                tvSTTOther.text = (position + 1).toString()
                tvNameCollateral.text = item.tenVatCamCo
                ivBtnDelete.setOnClickListener {
                    listCollateral.removeAt(position)
                    notifyDataSetChanged()
                }

            }

        }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val item = listCollateral.getOrNull(position)
                onClick?.invoke(item)
            }
        }
    }
}