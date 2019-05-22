package com.app.f49.activity.infoContract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoContractBinding
import com.app.f49.model.infocontract.InfoContractDTO
import kotlinx.android.synthetic.main.activity_info_contract.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import java.util.*

class InfoContractActivity : BaseMvvmActivity<ActivityInfoContractBinding, InfoContractViewModel, BaseNavigator>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InfoContractActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_contract
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.info_contract)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData()
    }

    private fun setData() {
        var data = InfoContractDTO().apply {
            numberContract = "HGNMGF"
            fullName = "Tran Quang Ngoc"
            phoneNumber = "0983636363"
            duNo = "35,234353,456"
            expiredDate = Date()
            plusMin = "234"
            interest = "323542343"
            fee = "3,4234,23"
            total = "2,234435,345"
            nhacNo = "435,3453,345"
            appointmentDate = Date()
            content = "han ky thanh toan"
            doDeLai = "iphone xmas"
        }
        mViewBinding?.item = data
    }
}