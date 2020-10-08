package com.app.f49.adapter.contract

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.model.createcontract.ThuocTinhTaiSanDTO
import kotlinx.android.synthetic.main.row_properties.view.*

class PropertiesCollateralAdapter(var properties: MutableList<ThuocTinhTaiSanDTO>) : RecyclerView.Adapter<PropertiesCollateralAdapter.ViewHolder>() {

    fun insertData(properties: MutableList<ThuocTinhTaiSanDTO>){
        this.properties = properties
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PropertiesCollateralAdapter.ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.row_properties, p0, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(p0: PropertiesCollateralAdapter.ViewHolder, p1: Int) {
        val item = properties.getOrNull(p1)
        if (item != null) {
            p0.binding(item)
        }
    }

    override fun getItemCount() = properties.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(properties: ThuocTinhTaiSanDTO) {
            itemView.apply {
                tvProperty.text = properties.title
                when(properties.dataType) {
                    "string" -> {
                        checkbox.visibility = View.GONE
                        edtProperty.visibility = View.VISIBLE
                        edtProperty.inputType = InputType.TYPE_CLASS_TEXT
                    }
                    "int" -> {
                        checkbox.visibility = View.GONE
                        edtProperty.visibility = View.VISIBLE
                        edtProperty.inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    "bool" -> {
                        checkbox.visibility = View.VISIBLE
                        edtProperty.visibility = View.GONE
                    }
                    "double" -> {
                        checkbox.visibility = View.GONE
                        edtProperty.visibility = View.VISIBLE
                        edtProperty.inputType = InputType.TYPE_CLASS_NUMBER
                    }
                }
                edtProperty.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        properties.value = edtProperty.text.toString()
                    }
                })
            }
        }
    }
}