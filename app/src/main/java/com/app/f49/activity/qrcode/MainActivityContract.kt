package yergalizhakhan.kz.qrcodescannerkotlin.presentation.activities.main

import com.app.f49.activity.qrcode.History
import com.app.f49.qrcode.mvp.BaseMvpPresenter
import com.app.f49.qrcode.mvp.BaseMvpView


object MainActivityContract {

    interface View: BaseMvpView {
        fun showSuccessScanningDialog(result: String)
        fun continueScanning()
    }

    interface Presenter: BaseMvpPresenter<View> {
        fun qrCodeScanned(history: History)
        fun searchByResultBtnPressed(result:String)
        fun copyResultBtnPressed(result: String)
        fun shareResultBtnPressed(result: String)
    }
}