package com.app.f49.base


interface BaseNavigator {
    fun showLoading(cancelable: Boolean)

    fun hideLoading()

    fun showErrorDialog(message: String?)

    fun showErrorDialogWithoutRetry(message: String?)

    fun showSuccessView()

    fun showToastErrorMsg(msg: String)

    fun showToastNoticeMsg(msg: String)

    fun hideKeyboard()

    fun showActionDialog(msg: String, action: () -> Unit)

    fun showAskDialog(msg: String, actionOk: () -> Unit)
}