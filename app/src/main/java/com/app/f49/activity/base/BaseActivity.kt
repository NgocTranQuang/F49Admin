package com.app.f49.activity.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.f49.R
import com.app.f49.base.BaseNavigator
import com.app.f49.custom.CustomProgressDialog
import com.app.f49.custom.DialogAsk
import com.app.f49.custom.DialogOption
import com.app.f49.custom.ErrorDialog
import com.app.f49.utils.GeneralUtils


open class BaseActivity : AppCompatActivity(), BaseNavigator {


    private var mProgressDialog: CustomProgressDialog? = null
    private var mErrorDialog: ErrorDialog? = null
    private var mActionDialog: DialogOption? = null
    private var mAskDialog: DialogAsk? = null
    var tvMessage: TextView? = null
    protected var mDialogWithMessage: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


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
//        supportActionBar?.customView = getMyToolbar()
        getMyToolbar()?.setContentInsetsAbsolute(0,0);

        getTitleToolbar()?.let {
            supportActionBar?.title = it
        }
//        supportActionBar?.setIcon(R.drawable.ic_phone)
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
        mActionDialog = DialogOption(this)
        mAskDialog = DialogAsk(this)
    }

    override fun showAskDialog(msg: String, actionOk: () -> Unit) {
        mAskDialog?.setErrorMsg(msg)
        mAskDialog?.setActionOk(actionOk)
        mAskDialog?.show()
    }

    override fun showActionDialog(msg: String, action: () -> Unit) {
        mActionDialog?.setErrorMsg(msg)
        mActionDialog?.setActionOk(action)
        mActionDialog?.show()
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

    protected fun showDialogWithMessage(message: String) {
        if (this.tvMessage == null) {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(false) // if you want user to wait for some process to finish,
            var view = layoutInflater.inflate(R.layout.layout_loading_dialog, null)
            this.tvMessage = view.findViewById(R.id.tvMessage)
            this.tvMessage?.text = message
            builder.setView(view)
            mDialogWithMessage = builder.create()
            mDialogWithMessage?.show()
        } else {
            this.tvMessage?.text = message
        }
    }

    protected fun hideDialogWithMesasge() {
        mDialogWithMessage?.hide()
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