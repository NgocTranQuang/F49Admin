package com.app.f49.custom

import android.arch.lifecycle.MutableLiveData
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.databinding.ItemBannerBinding
import com.app.f49.databinding.RowBannerMatchparentBinding
import extension.setOnSingleClickListener

class AdapterBanner(
    var items: MutableList<String>,
    var isSquareCardView: Boolean,
    var isImageType: Int,
    var eventClickImageBanner: ((View, Int) -> Unit)?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (isSquareCardView) {
            val binding: RowBannerMatchparentBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_banner_matchparent, parent, false)
            return ViewHolderMatchParent(binding)
        } else {
            val binding: ItemBannerBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_banner, parent, false)
            return ViewHolder(binding)
        }

    }

    fun setListImage(listImage: MutableList<String>, type: Int) {
        items = listImage
        isImageType = type
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(position)
        } else if (holder is ViewHolderMatchParent) {
            holder.bind(position)
        }
    }

    inner class ViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        var imageURL: MutableLiveData<String> = MutableLiveData()
        fun bind(position: Int) {
            binding.item = this
            binding.root.setOnSingleClickListener {
                eventClickImageBanner?.invoke(binding.img, position)
            }
            imageURL.value = items.getOrNull(position)
                ?: "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/09/12/11/naturo-monkey-selfie.jpg?w968"
            binding.executePendingBindings()
        }
    }

    inner class ViewHolderMatchParent(val binding: RowBannerMatchparentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imageURL: MutableLiveData<String> = MutableLiveData()
        var isImageType: MutableLiveData<Boolean> = MutableLiveData()
        fun bind(position: Int) {
            binding.item = this
            binding.cvBanner.radius = 0.0f
            binding.root.setOnSingleClickListener {
                eventClickImageBanner?.invoke(binding.img, position)
            }
//            isImageType.value = this@AdapterBanner.isImageType == ContentProductTypeEnum.IMAGE.getTypeNumber()
            imageURL.value = items.getOrNull(position)
                ?: "https://static.independent.co.uk/s3fs-public/thumbnails/image/2017/09/12/11/naturo-monkey-selfie.jpg?w968"
            binding.executePendingBindings()
        }
    }

}