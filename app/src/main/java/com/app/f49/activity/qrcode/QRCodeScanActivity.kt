package yergalizhakhan.kz.qrcodescannerkotlin.presentation.activities.main

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.app.f49.R
import com.app.f49.activity.infoContract.InfoContractActivity
import com.app.f49.model.infocontract.InfoQRCodeDTO
import com.app.f49.qrcode.mvp.BaseMvpActivity
import com.app.f49.utils.GeneralUtils
import com.google.gson.Gson
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QRCodeScanActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
    MainActivityContract.View, ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    private var flashState: Boolean = false
    private var dialog: AlertDialog? = null

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(R.layout.activity_qrcode)
        setUpActionbar()
//        mHistoryOrm = HistoryORM()
        initUI()
        setMarginTopForToolbar()
    }

    protected fun setMarginTopForToolbar() {
        tb?.let {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.qrcode_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (flashState) {
            item?.setIcon(R.drawable.ic_flash_on)
            showMessage(R.string.flashlight_turned_off)
            mScannerView?.setFlash(false)
            flashState = false
        } else {
            item?.setIcon(R.drawable.ic_flash_off)
            showMessage(R.string.flashlight_turned_on)
            mScannerView?.setFlash(true)
            flashState = true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    private fun initUI() {
        mScannerView = ZXingScannerView(this)
        frmContent.addView(mScannerView)
//        btnLight.setOnClickListener(this)
    }

//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.btnLight -> {
//                if (flashState) {
//                    v.setBackgroundResource(R.drawable.ic_flash_on)
//                    showMessage(R.string.flashlight_turned_off)
//                    mScannerView?.setFlash(false)
//                    flashState = false
//                } else {
//                    v.setBackgroundResource(R.drawable.ic_flash_off)
//                    showMessage(R.string.flashlight_turned_on)
//                    mScannerView?.setFlash(true)
//                    flashState = true
//                }
//            }
//
//        }
//    }

    private fun setUpActionbar() {
        setSupportActionBar(tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.customView = getMyToolbar()
        tb?.setContentInsetsAbsolute(0, 0);

//        getTitleToolbar()?.let {
        supportActionBar?.title = getString(R.string.search)
//        }
//        supportActionBar?.setIcon(R.drawable.ic_phone)
        tb?.setNavigationOnClickListener {
            finish()
        }
    }

    override fun handleResult(result: Result?) {
        var json = result?.text?.toString()
        val infoQRCodeDTO = Gson().fromJson(json, InfoQRCodeDTO::class.java)
        InfoContractActivity.start(this, infoQRCodeDTO.Id.toString(), null)
        finish()
    }

    override fun showSuccessScanningDialog(result: String) {
//        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scan_success, null)
//        val dialogBuilder = AlertDialog.Builder(this)
//                .setView(mDialogView)
//        mDialogView.tvResult.text = result
//        mDialogView.btnSearch.setOnClickListener {
//            mPresenter.searchByResultBtnPressed(result)
//        }
//        mDialogView.btnCopy.setOnClickListener {
//            mPresenter.copyResultBtnPressed(result)
//        }
//        mDialogView.btnShare.setOnClickListener {
//            mPresenter.shareResultBtnPressed(result)
//        }
//        dialog = dialogBuilder.create()
//        dialog?.setOnCancelListener {
//            continueScanning()
//        }
//        dialog?.show()
    }

    override fun continueScanning() {
        dialog?.dismiss()
        mScannerView?.resumeCameraPreview(this)
    }
}
