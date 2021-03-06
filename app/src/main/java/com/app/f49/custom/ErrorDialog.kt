package com.app.f49.custom

import android.content.Context
import android.support.v7.app.AlertDialog
import com.app.f49.R


class ErrorDialog(private val mActivity: Context) {
    private var mAlertDialog: AlertDialog? = null

    init {
        mAlertDialog = initErrorDialog()
    }

    fun initErrorDialog(): AlertDialog {

        val builder1 = AlertDialog.Builder(mActivity)
        builder1.setMessage(mActivity.getString(R.string.server_error))
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            mActivity.getString(R.string.yes)
        ) { dialog, id -> dialog.cancel() }

//        builder1.setNegativeButton(
//            mActivity.getString(R.string.no)
//        ) { dialog, functionKey -> dialog.cancel() }

        val alert11 = builder1.create()
        return alert11
    }

    fun setCancelable(isCancelable: Boolean) {
        mAlertDialog?.setCancelable(isCancelable)
    }

    fun setErrorMsg(errorMsg: String?) {
        mAlertDialog?.setMessage(errorMsg)
    }

    fun show() {
        mAlertDialog?.show()
    }

}

class DialogOption(private val mActivity: Context) {
    private var mAlertDialog: AlertDialog? = null
    private var clickOk: (() -> Unit)? = null

    init {
        mAlertDialog = initErrorDialog()
    }

    fun initErrorDialog(): AlertDialog {

        val builder1 = AlertDialog.Builder(mActivity)
        builder1.setMessage(mActivity.getString(R.string.server_error))
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            mActivity.getString(R.string.yes)
        ) { dialog, id ->
            dialog?.cancel()
        }
        builder1.setOnCancelListener {
            clickOk?.invoke()
        }
//        builder1.setNegativeButton(
//            mActivity.getString(R.string.no)
//        ) { dialog, functionKey -> dialog.cancel() }

        val alert11 = builder1.create()
        return alert11
    }

    fun setActionOk(clickOk: (() -> Unit)) {
        this.clickOk = clickOk
    }

    fun setCancelable(isCancelable: Boolean) {
        mAlertDialog?.setCancelable(isCancelable)
    }

    fun setErrorMsg(errorMsg: String?) {
        mAlertDialog?.setMessage(errorMsg)
    }

    fun show() {
        mAlertDialog?.show()
    }

}

class DialogAsk(private val mActivity: Context) {
    private var mAlertDialog: AlertDialog? = null
    private var clickOk: (() -> Unit)? = null

    init {
        mAlertDialog = initErrorDialog()
    }

    fun initErrorDialog(): AlertDialog {

        val builder1 = AlertDialog.Builder(mActivity)
        builder1.setMessage(mActivity.getString(R.string.server_error))
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            mActivity.getString(R.string.yes)
        ) { dialog, id ->
            clickOk?.invoke()
            dialog?.cancel()
        }
//        builder1.setOnCancelListener {
//            clickOk?.invoke()
//        }
        builder1.setNegativeButton(
            mActivity.getString(R.string.no)
        ) { dialog, functionKey -> dialog.cancel() }

        val alert11 = builder1.create()
        return alert11
    }

    fun setActionOk(clickOk: (() -> Unit)) {
        this.clickOk = clickOk
    }

    fun setCancelable(isCancelable: Boolean) {
        mAlertDialog?.setCancelable(isCancelable)
    }

    fun setErrorMsg(errorMsg: String?) {
        mAlertDialog?.setMessage(errorMsg)
    }

    fun show() {
        mAlertDialog?.show()
    }

}