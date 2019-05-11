package vn.com.ttc.ecommerce.custom

import android.app.Activity
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.app.f49.R
import android.content.DialogInterface
import android.content.Context


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
//        ) { dialog, id -> dialog.cancel() }

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
