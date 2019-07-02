package com.app.f49.activity.base

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.app.f49.base.BaseMvvmAndroidViewModel
import com.app.f49.base.BaseNavigator
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<A : ViewDataBinding, B : BaseMvvmAndroidViewModel<N>, N : BaseNavigator> :
    BaseActivity() {
    var mViewBinding: A? = null
    var mViewModel: B? = null


    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = ViewModelProviders.of(this).get(getViewModelClass())
        mViewBinding?.lifecycleOwner = this
        setViewModelToViewBinding(mViewModel)
        configView()
    }


    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<B> {
        return ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<B>)
    }

    override fun onDestroy() {
        setViewModelToViewBinding(null)
        mViewModel?.onDestroy()
        mViewModel = null
        mViewBinding = null
        super.onDestroy()
    }

    protected fun setViewModelToViewBinding(value: B?) {
        try {
            mViewBinding?.let { vb ->
                mViewModel?.let { vm ->
                    val method = vb.javaClass.getDeclaredMethod("setViewModel", vm.javaClass)
                    method.invoke(vb, value)
                }
            }
        } catch (ex: Throwable) {
        }
    }


}