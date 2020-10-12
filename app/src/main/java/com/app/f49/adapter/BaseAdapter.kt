package com.app.f49.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.f49.extension.setOnSingleClickListener
import java.lang.reflect.ParameterizedType

open class BaseAdapter<A : ViewDataBinding, T>(list: MutableList<T>, var rv: RecyclerView) : LoadMoreAdapter<T>(list, rv) {
    override fun onCreateViewHolderItem(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = getViewBindingInstance(LayoutInflater.from(p0.context), p0)
        return ViewHolder(view)
    }

    override fun onBindViewHolderItem(p0: RecyclerView.ViewHolder, p1: Int) {
        if (p0 is BaseAdapter<*, *>.ViewHolder) {
            p0.bind(p1)
        }
    }

    inner class ViewHolder(val binding: A) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            var item = items?.get(position)
            itemView.setOnSingleClickListener {
                onEventClickListener(itemView.context,item)
            }
            initModel(item, position)
            setViewModelToViewBinding(binding, item)
        }
    }

    open fun onEventClickListener(context : Context,item: T?) {

    }

    open fun initModel(item: T?, position: Int) {

    }

    private fun getViewBindingInstance(layoutInflater: LayoutInflater, container: ViewGroup?): A {
        val clazz = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<A>)
        val method =
            clazz.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        return method.invoke(clazz, layoutInflater, container, false) as A
    }

    private fun setViewModelToViewBinding(viewBinding: A, value: T?) {
        try {
            viewBinding?.let { vb ->
                val clazz = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>)
                clazz?.let { vm ->
                    val method = vb.javaClass.getDeclaredMethod("setItem", vm)
                    method.invoke(vb, value)
                    viewBinding.executePendingBindings()
                }
            }
        } catch (ex: Throwable) {
        }
    }
}