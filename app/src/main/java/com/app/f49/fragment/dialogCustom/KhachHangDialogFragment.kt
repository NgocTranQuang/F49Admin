package com.app.f49.fragment.dialogCustom

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.app.f49.R
import com.app.f49.activity.creatingContract.KhachHangViewModel
import com.app.f49.adapter.contract.CustomerAdapter
import com.app.f49.model.createcontract.KhachHangDTO
import kotlinx.android.synthetic.main.fragment_dialog_timkiem_khachhang.*
import kotlinx.android.synthetic.main.fragment_dialog_timkiem_khachhang.view.*

class KhachHangDialogFragment : DialogFragment() {
    private var khachHangViewModel: KhachHangViewModel? = null
    private var customerAdapter: CustomerAdapter? = null
    var customer: ((KhachHangDTO?) -> Unit)? = null

    companion object {
        fun newInstance( customer: ((KhachHangDTO?) -> Unit)? ): KhachHangDialogFragment {
            var fm = KhachHangDialogFragment();
            fm.customer = customer
            return fm
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_timkiem_khachhang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)

        khachHangViewModel = ViewModelProviders.of(this).get(KhachHangViewModel::class.java)
        khachHangViewModel?.timKiem("")
        observe()
        customerAdapter = CustomerAdapter(mutableListOf())
        rvInfoCustomer.layoutManager = GridLayoutManager(activity, 1)
        rvInfoCustomer.setHasFixedSize(true)
        rvInfoCustomer.adapter = customerAdapter
        setupClickListeners(view)
    }

    private fun observe() {
        khachHangViewModel?.khachHang?.observe(this, Observer {
            customerAdapter?.insertData(it ?: mutableListOf())
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
//        view.tvTitle.text = arguments?.getString(KEY_TITLE)
//        view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)
    }

    private fun setupClickListeners(view: View) {
        view.imgSearchDialog.setOnClickListener {
            khachHangViewModel?.timKiem(view.edtCustomerNameChoose.text.toString())
        }
        customerAdapter?.onClick = {
            customer?.invoke(it)
            dismiss()
        }
    }
}