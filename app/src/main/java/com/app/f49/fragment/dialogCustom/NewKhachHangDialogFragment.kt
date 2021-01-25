package com.app.f49.fragment.dialogCustom

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.app.f49.R
import com.app.f49.activity.creatingContract.CreateContractActivity
import com.app.f49.activity.creatingContract.KhachHangViewModel
import com.app.f49.adapter.contract.CustomerAdapter
import com.app.f49.model.createcontract.KhachHangDTO
import kotlinx.android.synthetic.main.fragment_dialog_taomoi_khachhang.view.*

class NewKhachHangDialogFragment : DialogFragment() {
    private var khachHangViewModel: KhachHangViewModel? = null
    private var customerAdapter: CustomerAdapter? = null
    var customer: ((KhachHangDTO?) -> Unit)? = null

//    companion object {
//        fun newInstance( customer: ((KhachHangDTO?) -> Unit)? ): NewKhachHangDialogFragment {
//            var fm = NewKhachHangDialogFragment()
//            fm.customer = customer
//            return fm
//        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_taomoi_khachhang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

        khachHangViewModel = ViewModelProviders.of(this).get(KhachHangViewModel::class.java)
        observe()

        setupClickListeners(view)
    }

    private fun observe() {

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
//        view.tvTitle.text = arguments?.getString(KEY_TITLE)
//        view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)
    }

    private fun setupClickListeners(view: View) {
        view.tvSaveCustomer.setOnClickListener {
            if (view.edtCustomerName.text.isEmpty() || view.edtPhoneNumber.text.isEmpty()){
                (activity as CreateContractActivity).showErrorDialog(getString(R.string.not_empty_name))
            }else{
                var customerDTO = KhachHangDTO().apply {
                    hoTen = view.edtCustomerName.text.toString()
                    dienThoai = view.edtPhoneNumber.text.toString()
                }
                customer?.invoke(customerDTO)
                dismiss()
            }

        }
        view.tvDong.setOnClickListener {
            dismiss()
        }
    }
}