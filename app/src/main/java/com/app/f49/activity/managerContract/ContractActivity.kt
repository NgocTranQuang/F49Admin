package com.app.f49.activity.managerContract

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.adapter.contract.ContractAdapter
import com.app.f49.bottomsheet.ContractBottomSheet
import com.app.f49.databinding.ActivityManageContractBinding
import com.app.f49.model.managercontract.ManagerContractDTO
import extension.init
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_manage_contract.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class ContractActivity : BaseMvvmActivity<ActivityManageContractBinding, ContractViewModel, BaseNavigator>() {
    var adapter: ContractAdapter? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ContractActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_manage_contract
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.manager_contact)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTablayout()
        initRV()
        initSpiner()
        evenClickListener()
    }

    private fun evenClickListener() {
        btnFilter.setOnSingleClickListener {
            ContractBottomSheet.show(supportFragmentManager)
        }
    }

    private fun initSpiner() {
//        spContract.setList(Base.LIST_STRING_DEFAULT)
    }

    private fun initRV() {
        rvContract.init(space = R.dimen.height_line_size)
        adapter = ContractAdapter(getData())
        rvContract.adapter = adapter
    }

    private fun getData(): MutableList<ManagerContractDTO> {
        var list = mutableListOf(ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
            bgColor = "#ff0000"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
            bgColor = "#fff000"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
        }, ManagerContractDTO().apply {
            name = "HDNGHF_Nguyen van A"
            upDown = "-524"
            duNo = "10,000,000"
            total = "3,2324,454"
            bgColor = "#ff0000"
        })
        return list
    }

    private fun initTablayout() {
        tbLayout.addTab(tbLayout.newTab().setText(getStringBy(R.string.hd_mo)));
        tbLayout.addTab(tbLayout.newTab().setText(getStringBy(R.string.hd_thanhly)));
        tbLayout.addTab(tbLayout.newTab().setText(getStringBy(R.string.hd_tat_toan)));
    }

    fun getStringBy(id: Int): String {
        return resources.getString(id)
    }
}