package com.app.f49.bottomsheet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R
import com.app.f49.activity.managerContract.ContractViewModel
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.bottomsheet_contract.*

class ContractBottomSheet : BottomSheetDialogFragment() {

    var result: ((String?, String?, String?) -> Unit)? = null
    var idNguoiQLHDChoose: String? = null
    var idStatusChoose: String? = null
    var viewModel: ContractViewModel? = null

    companion object {
        fun show(fm: FragmentManager, result: ((String?, String?, String?) -> Unit)? = null) {
            val bottomSheetFragment = ContractBottomSheet()
            bottomSheetFragment.show(fm, bottomSheetFragment.tag)
            bottomSheetFragment.result = result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_contract, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpiner()
        obsever()
        clickListener()
    }

    private fun clickListener() {
        cvDatLai.setOnSingleClickListener {
            edtSeach.setText("")
        }
        btnDone.setOnSingleClickListener {
            result?.invoke(edtSeach.text.toString(), idNguoiQLHDChoose, idStatusChoose)
            dismiss()
        }
    }

    private fun obsever() {
        viewModel = ViewModelProviders.of(activity as FragmentActivity).get(ContractViewModel::class.java)
        viewModel?.listNguoiQLHDDTO?.observe(this, Observer {
            it?.let {
//                spPerson.setList(it.map { it.hoTen }.toMutableList(), 0)
            }
        })
        viewModel?.listStatusContract?.observe(this, Observer {
            it?.let {
                spStatus.setList(it.map { it.value }.toMutableList(), 0)
            }
        })
    }

    private fun initSpiner() {
//        spPerson.selectedItemListener {
//            idNguoiQLHDChoose = viewModel?.listNguoiQLHDDTO?.value?.getOrNull(it)?.id
//        }
        spStatus.selectedItemListener {
            idStatusChoose = viewModel?.listStatusContract?.value?.getOrNull(it)?.id
        }

    }
}