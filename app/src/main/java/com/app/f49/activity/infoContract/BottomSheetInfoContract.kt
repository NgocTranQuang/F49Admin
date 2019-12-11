package com.app.f49.activity.infoContract

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.TypeActionChiTietHDCDEnum
import com.app.f49.activity.exchangeHistory.ExchangeHistoryActivity
import com.app.f49.activity.thulai.ThuLaiActivity
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.bottomsheet_info_contract.*

class BottomSheetInfoContract : BottomSheetDialogFragment() {
    var idHopDong: Int? = null
    var soHopDong: String? = null
    var idKH: String? = null
    var tenKH: String? = null

    companion object {
        fun show(fm: FragmentManager, idHopDong: Int?, soHopDong: String?) {
            val bottomSheetFragment = BottomSheetInfoContract()
            bottomSheetFragment.idHopDong = idHopDong
            bottomSheetFragment.soHopDong = soHopDong
            bottomSheetFragment.show(fm, bottomSheetFragment.tag)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_info_contract, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventClickListener()
        var viewModel = ViewModelProviders.of(activity as FragmentActivity).get(InfoContractViewModel::class.java)
        var hopdongDTO = viewModel?.infoContract?.value
        tvCountLichSuGiaoDich.text = "( ${hopdongDTO?.countLichSuGiaodich} )"
        tvCountLichSuVay.text = "( ${hopdongDTO?.countLichSuVay} )"
        idHopDong = hopdongDTO?.id?.toInt()
        soHopDong = hopdongDTO?.numberContract
        idKH = hopdongDTO?.idKhachHang
        tenKH = hopdongDTO?.fullName
    }

    fun startExchangeActivity(type: Int) {
        ExchangeHistoryActivity.start(activity as FragmentActivity, idHopDong, soHopDong, idKH?.toInt(), tenKH, type)
    }

    private fun eventClickListener() {
        vExchangeHistory.setOnSingleClickListener {
            startExchangeActivity(TypeActionChiTietHDCDEnum.EXCHANGE.value)
        }
        vBorrowHistory.setOnSingleClickListener {
            startExchangeActivity(TypeActionChiTietHDCDEnum.BORROW.value)

        }
        vThuLai.setOnSingleClickListener {
            ThuLaiActivity.start(activity, idHopDong)
        }
    }
}