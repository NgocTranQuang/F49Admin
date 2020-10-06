package com.app.f49.fragment.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.MainViewModel
import com.app.f49.activity.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import java.lang.reflect.ParameterizedType


open abstract class BaseMvvmFragment<A : ViewDataBinding, B : BaseMvvmAndroidViewModel<N>, N : BaseNavigator> : BaseFragment() {
    var viewBinding: A? = null
    var viewModel: B? = null

    interface OnBackPressedListener {
        fun onBackPressed(): Boolean = false
    }

    open fun canShowActionBar(): Boolean = true

    open fun getScreenTitle(): String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = getViewBindingInstance(inflater, container)
//        val child = inflater.inflate(R.layout.fragment_dashboard, null)
//        DataBindingUtil.getBinding<A>(child)
        return viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configViewModel()
    }

    private fun configViewModel() {
        viewModel = getViewModelProvider().get(getViewModelClass())
        viewBinding?.lifecycleOwner = this
        setViewModelToViewBinding(viewModel)
    }

    open fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProviders.of(this)
    }

    override fun onDestroy() {
        setViewModelToViewBinding(null)
        viewModel?.onDestroy()
        viewModel = null
        viewBinding = null
        super.onDestroy()
    }

    private fun getViewBindingInstance(layoutInflater: LayoutInflater, container: ViewGroup?): A {
      var a =  DataBindingUtil.inflate<A>(
            layoutInflater, getLayoutResource(), container, false);
        return a
//        val clazz = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<A>)
//        val method =
//            clazz.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
//        return method.invoke(clazz, layoutInflater, container, false) as A
    }

    private fun setViewModelToViewBinding(value: B?) {
        try {
            viewBinding?.let { vb ->
                viewModel?.let { vm ->
                    val method = vb.javaClass.getDeclaredMethod("setViewModel", vm.javaClass)
                    method.invoke(vb, value)
                }
            }
        } catch (ex: Throwable) {
        }
    }

    private fun getViewModelClass(): Class<B> {
        return ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<B>)
    }

    open fun refreshData() {
        // Implement in children class
    }

    fun getMainViewModel(): MainViewModel {
        return ViewModelProviders.of(activity as FragmentActivity).get(MainViewModel::class.java)
    }
}