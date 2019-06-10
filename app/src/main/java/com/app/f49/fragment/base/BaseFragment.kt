package vn.com.ttc.ecommerce.fragment.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.utils.GeneralUtils
import vn.com.ttc.ecommerce.activity.base.BaseActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.custom.CustomProgressDialog

open class BaseFragment : Fragment(), BaseNavigator {


    private var mProgressDialog: CustomProgressDialog? = null
    open fun getLayoutResource(): Int? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResource() ?: 0, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBase()
    }

    protected fun initBase() {
        mProgressDialog = CustomProgressDialog(activity!!, R.style.ProgressDialogDim)
    }

    override fun showLoading(cancelable: Boolean) {
        mProgressDialog!!.setCancelable(cancelable)
        mProgressDialog!!.show()
    }

    override fun hideLoading() {
        mProgressDialog!!.dismiss()
    }

    override fun showErrorDialog(message: String?) {
        (activity as BaseActivity).showErrorDialog(message)
    }
    override fun showActionDialog(msg: String, action: () -> Unit) {
        (activity as BaseActivity).showActionDialog(msg,action)
    }

    override fun showErrorDialogWithoutRetry(message: String?) {
        showErrorDialog(message)
    }

    override fun showSuccessView() {
        (activity as BaseActivity).showSuccessView()
    }

    override fun showToastErrorMsg(msg: String) {
        (activity as BaseActivity).showToastErrorMsg(msg)
    }

    override fun showToastNoticeMsg(msg: String) {
        (activity as BaseActivity).showToastNoticeMsg(msg)
    }

    override fun hideKeyboard() {
        GeneralUtils.hideKeyboard(activity!!, view)
    }

}