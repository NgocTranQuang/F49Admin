package com.app.f49.fragment.dialogCustom

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.app.f49.R
import com.app.f49.activity.creatingContract.KhachHangViewModel
import extension.selectedItemListener
import extension.setList
import kotlinx.android.synthetic.main.fragment_dialog_collateral.*

class TaiSanDialogFragment: DialogFragment() {
    private var khachHangViewModel: KhachHangViewModel? = null
    companion object {
        fun newInstance(): TaiSanDialogFragment {
            return TaiSanDialogFragment()
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_collateral, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
        khachHangViewModel = ViewModelProviders.of(this).get(KhachHangViewModel::class.java)
        khachHangViewModel?.layDanhSachTaiSan()
        khachHangViewModel?.taiSan?.observe(this, Observer {
            spSelectTaiSan.setList(it?.map { it.tenVatCamCo }?.toMutableList(),0)
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }
    private fun setupView(view: View) {

    }

    private fun setupClickListeners(view: View) {
        spSelectTaiSan.selectedItemListener {
            var list =  khachHangViewModel?.taiSan?.value
            list?.get(it)?.tenVatCamCo
        }
    }
}