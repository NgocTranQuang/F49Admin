package com.app.f49.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.TypeLoad
import com.app.f49.utils.Constants
import com.app.f49.utils.PreferenceUtils
import com.app.f49.extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.row_loadmore.view.*


open abstract class LoadMoreAdapter<T>(
    var items: MutableList<T>?,
    rv: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_LOAD_MORE = -2
    private val TYPE_ITEM = -1
    private var isAvailableToLoadMore = true
    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loadMoreListener: (() -> Unit)? = null
    private var pageSize = Constants.PAGE_SIZE_DEFAULT

    init {
        setLoadMore(rv)
    }

    private fun setLoadMore(recyclerView: RecyclerView) {
        pageSize = PreferenceUtils.getInt(recyclerView.context, PreferenceUtils.KEY_PAGE_SIZE, Constants.PAGE_SIZE_DEFAULT)
        if (getTypeLoad() == TypeLoad.AUTO_LOAD) {
            val linearLayoutManager = recyclerView?.getLayoutManager() as LinearLayoutManager
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (isAvailableToLoadMore) {
                        totalItemCount = linearLayoutManager.itemCount
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                            isLoading = true
                            loadMoreListener?.invoke()
                        }
                    }
                }
            })
        }
    }

    fun setLoadMoreListener(listener: (() -> Unit)?) {
        this.loadMoreListener = listener
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    open fun getTypeLoad(): TypeLoad {
        return TypeLoad.AUTO_LOAD
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_LOAD_MORE) {
            val layoutInflater = LayoutInflater.from(p0.context)
            val view = layoutInflater.inflate(R.layout.row_loadmore, p0, false)
            return ViewHolderLoadMore(view)
        } else {
            return onCreateViewHolderItem(p0, viewType)
        }
    }

    abstract fun onCreateViewHolderItem(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindViewHolderItem(p0: RecyclerView.ViewHolder, p1: Int)
    override fun getItemCount(): Int {
        if (items == null) {
            return 0
        }
        if ((items?.size ?: 0 )< pageSize) {
            return items?.size ?: 0
        }
        return (items?.size ?: 0) + 1
    }

    fun setShowingLoadMore(show: Boolean) {
        isAvailableToLoadMore = show
    }

    fun finishedLoadMore() {
        setShowingLoadMore(false)
    }

    override fun getItemViewType(position: Int): Int {
        if (itemCount < pageSize) {
            return TYPE_ITEM
        }
        if (position == itemCount - 1) {
            return TYPE_LOAD_MORE
        }
        return TYPE_ITEM
    }

    fun setData(listImage: MutableList<T>) {
        items?.addAll(listImage)
        setIsLoading(false)
        if ((listImage?.size ?: 0 )< pageSize) {
            setShowingLoadMore(false)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is LoadMoreAdapter<*>.ViewHolderLoadMore) {
            p0.bind()
        } else {
            onBindViewHolderItem(p0, p1)
        }

    }

    inner class ViewHolderLoadMore(val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind() {
            if (isAvailableToLoadMore) {
                if (getTypeLoad() == TypeLoad.CLICK_TO_LOAD) {
                    initViewClickToLoadMore()
                } else {
                    itemView.pb_loadmore.visibility = View.VISIBLE
                }
            } else {
                initFinishedLoadMore()
            }
        }

        fun initViewClickToLoadMore() {
            showTextLoadMore(R.string.view_more)
            itemView.tvNoData.setOnSingleClickListener {
                loadMoreListener?.invoke()
                initViewLoading()
            }
        }

        fun initViewLoading() {
            itemView.pb_loadmore.visibility = View.VISIBLE
            itemView.tvNoData.visibility = View.GONE
        }

        fun initFinishedLoadMore() {
            showTextLoadMore(R.string.no_data_to_loadmore)
            itemView.tvNoData.isClickable = false
        }

        fun showTextLoadMore(text: Int) {
            itemView.pb_loadmore.visibility = View.GONE
            itemView.tvNoData.visibility = View.VISIBLE
            itemView.tvNoData.setText(binding.context.getString(text))
        }
    }

}