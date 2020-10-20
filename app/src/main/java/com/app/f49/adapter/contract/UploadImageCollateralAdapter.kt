package com.app.f49.adapter.contract

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.model.createcontract.PropertiesImageDTO
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_image.view.*

class UploadImageCollateralAdapter(var listImage: MutableList<PropertiesImageDTO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onClick: ((PropertiesImageDTO?) -> (Unit))? = null

    companion object {
        private const val TYPE_IMAGE_EMPTY = 0
        private const val TYPE_IMAGE_NORMAL = 1
    }


    fun insertData(image: MutableList<PropertiesImageDTO>) {
        this.listImage.addAll(listImage.size-1,image)
        notifyDataSetChanged()
    }
   override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return if (p1 == TYPE_IMAGE_EMPTY) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false)
            ViewHolderImageEmpty(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false)
            ViewHolderImageNormal(itemView)
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val item = listImage.getOrNull(p1)

        p0.apply {
            if(p0 is ViewHolderImageEmpty){
                p0.binding()
            }else{
                if (item != null) {
                    (p0 as ViewHolderImageNormal).binding(item,p1)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == (listImage.size - 1)) {
            TYPE_IMAGE_EMPTY
        } else {
            TYPE_IMAGE_NORMAL
        }
    }

    override fun getItemCount() = listImage.size

    inner class ViewHolderImageEmpty(var v: View) : RecyclerView.ViewHolder(v) {
        fun binding() {
            v.apply {
                ivDelete.visibility = View.GONE
            }
        }

        init {
            v.setOnClickListener {
                val position = adapterPosition
                val item = listImage[position]
                onClick?.invoke(item)
            }

        }
    }


    inner class ViewHolderImageNormal(var v: View) : RecyclerView.ViewHolder(v) {
        fun binding(item: PropertiesImageDTO?, position: Int) {
            v.apply {
                ivAdd.visibility = View.GONE
                ivDelete.setOnClickListener {
                    listImage.removeAt(position)
                    notifyDataSetChanged()
                }
                Glide.with(ivTaisan.context).load(item?.dataAsURLs).into(ivTaisan)
            }
        }
    }
}