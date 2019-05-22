package vn.com.ttc.ecommerce.activity.base

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.app.f49.R
import com.app.f49.utils.GeneralUtils
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.custom.CustomProgressDialog
import vn.com.ttc.ecommerce.custom.ErrorDialog


open class BaseActivity : AppCompatActivity(), BaseNavigator {
    private var mProgressDialog: CustomProgressDialog? = null
    private var mErrorDialog: ErrorDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    }

    protected fun configView() {
        initBase()
        setUpActionbar()
        setMarginTopForToolbar()
    }

    private fun setUpActionbar() {
        setSupportActionBar(getMyToolbar())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        getTitleToolbar()?.let {
            supportActionBar?.title = it
        }
        getMyToolbar()?.setNavigationOnClickListener {
            handleBackToolbar()
        }
    }

    open fun getMyToolbar(): Toolbar? {
        return null
    }

    open fun getTitleToolbar(): String? {
        return null
    }

    open fun handleBackToolbar() {
        finish()
    }

    protected fun setMarginTopForToolbar() {
        getMyToolbar()?.let {
            val toolbarParams = if (it.layoutParams is FrameLayout.LayoutParams) {
                it.layoutParams as FrameLayout.LayoutParams
            } else if (it.layoutParams is LinearLayout.LayoutParams) {
                it.layoutParams as LinearLayout.LayoutParams
            } else if (it.layoutParams is AppBarLayout.LayoutParams) {
                it.layoutParams as AppBarLayout.LayoutParams
            } else {
                it.layoutParams as RelativeLayout.LayoutParams
            }
            toolbarParams.topMargin = GeneralUtils.getStatusBarHeight(this)
        }

    }

    protected fun initBase() {
        mProgressDialog = CustomProgressDialog(this, R.style.ProgressDialogDim)
        mErrorDialog = ErrorDialog(this)
    }


    override fun showLoading(cancelable: Boolean) {
        mProgressDialog?.setCancelable(cancelable)
        mProgressDialog?.show()
    }

    override fun hideLoading() {
        mProgressDialog?.dismiss()
    }

    override fun showErrorDialog(message: String?) {
        mErrorDialog?.setErrorMsg(message)
        mErrorDialog?.show()
    }

    override fun showErrorDialogWithoutRetry(message: String?) {
        showErrorDialog(message)
    }

    override fun showSuccessView() {
        showToastNoticeMsg(getString(R.string.success))
    }

    override fun showToastErrorMsg(msg: String) {
        showCustomToast(msg, R.color.colorRed)
    }

    override fun showToastNoticeMsg(msg: String) {
        showCustomToast(msg, R.color.colorAccent)
    }

    @Suppress("DEPRECATION")
    private fun showCustomToast(msg: String, bgColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            GeneralUtils.showCustomToast(this, msg, getColor(bgColor))
        } else {
            GeneralUtils.showCustomToast(this, msg, resources.getColor(bgColor))
        }
    }

    override fun hideKeyboard() {
        GeneralUtils.hideKeyboard(this, window.decorView.findViewById(android.R.id.content))
    }

//    protected fun addFragment(fragment: Fragment?, tag: String?) {
//        if (fragment == null) {
//            return
//        }
//        hideKeyboard()
//        val fragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        for (fragmentInStack in fragmentManager.fragments) {
//            transaction.hide(fragmentInStack)
//        }
//        var existedFragment = fragmentManager.findFragmentByTag(tag);
//        if (fragmentManager.findFragmentByTag(tag) != null) {
//            mCurrentShowedFragment = existedFragment!!
//            supportFragmentManager.popBackStack(tag, 0)
////            transaction.addToBackStack(tag)
//            transaction.show(existedFragment).commitAllowingStateLoss()
//        } else {
//            mCurrentShowedFragment = fragment
//            transaction.add(R.functionKey.content_main, fragment, tag)
//            transaction.addToBackStack(tag)
//            transaction.commitAllowingStateLoss()
//        }
//    }
//
//    protected fun addFragment(fragment: Fragment) {
//        addFragment(fragment, fragment.javaClass.canonicalName)
//    }
//
//    protected fun addFragmentToContent(fragment: Fragment) {
//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()
//        ft.add(android.R.functionKey.content, fragment)
//        ft.addToBackStack(fragment.javaClass.canonicalName)
//        ft.commitAllowingStateLoss()
//    }
//
//    protected fun hideFragmentInStack() {
//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()
//        for (fragmentInStack in fm.fragments) {
//            ft.hide(fragmentInStack)
//        }
//        ft.commit()
//    }

}