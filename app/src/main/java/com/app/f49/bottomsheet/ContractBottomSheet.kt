package com.app.f49.bottomsheet

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.f49.R

class ContractBottomSheet : BottomSheetDialogFragment() {


    companion object {
        fun show(fm: FragmentManager, result: ((String, String) -> Unit)? = null) {
            val bottomSheetFragment = ContractBottomSheet()
            bottomSheetFragment.show(fm, bottomSheetFragment.tag)
//            bottomSheetFragment.result = result
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_contract, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpiner()
    }

    private fun initSpiner() {
//        spPerson.setList(Base.LIST_STRING_DEFAULT)
//        spStatus.setList(Base.LIST_STRING_DEFAULT)
    }
}