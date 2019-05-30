package com.app.f49.activity.infoContract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoContractBinding
import com.app.f49.model.HopDongCamDoDTO
import com.app.f49.model.infocontract.InfoContractDTO
import kotlinx.android.synthetic.main.activity_info_contract.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class InfoContractActivity : BaseMvvmActivity<ActivityInfoContractBinding, InfoContractViewModel, BaseNavigator>() {
    companion object {
        val KEY_DATA = "KEY_DATA"
        fun start(context: Context, hdcmDTO: HopDongCamDoDTO) {
            context.startActivity(Intent(context, InfoContractActivity::class.java).putExtra(KEY_DATA, hdcmDTO))
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
        var hdcmDTO = intent?.getSerializableExtra(KEY_DATA) as? HopDongCamDoDTO
        hdcmDTO?.let {
            var data = InfoContractDTO().apply {
                numberContract = hdcmDTO.soHopDong
                fullName = hdcmDTO.tenKhachHang
                phoneNumber = hdcmDTO.dienThoaiKhachHang
                duNo = hdcmDTO.duNoHienTai
                expiredDate = hdcmDTO.ngayDenHan
                plusMin = hdcmDTO.soNgayQuaHan
                interest = hdcmDTO.laiPhaiThuFake
                fee = hdcmDTO.phiPhaiThuFake
                total = hdcmDTO.laiPhaiThu?.toString()
                nhacNo = hdcmDTO.ngayNhacNo
                appointmentDate = hdcmDTO.ngayDenHan
                content = hdcmDTO.noiDung
                doDeLai = hdcmDTO.doDeLai
            }
            mViewBinding?.item = data
        }
    }
}